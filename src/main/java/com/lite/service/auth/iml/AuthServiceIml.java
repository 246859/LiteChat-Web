package com.lite.service.auth.iml;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lite.dao.authDao.AuthMapper;
import com.lite.dto.ResponseResult;
import com.lite.dto.Token;
import com.lite.entity.auth.LoginUser;
import com.lite.entity.auth.User;
import com.lite.service.auth.AuthService;
import com.lite.utils.*;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
public class AuthServiceIml implements AuthService {

    @Autowired
    AuthMapper mapper;

    @Autowired
    RedisCache cache;

    @Override
    public ResponseResult<Token> login(User user,String ip) {

        String userName = user.getUserName();
        String password = user.getPassword();

        //根据表单输入的用户名与密码进行验证
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        User temp = mapper.selectOne(queryWrapper);//查询用户表


        if (Objects.isNull(temp) || Objects.isNull(password) || !PasswordEncoder.enCode(password).equals(temp.getPassword())) {//验证失败则返回错误代码
            return new ResponseResult<>(LiteHttpExceptionStatus.LOGIN_FAIL.code(), LiteHttpExceptionStatus.LOGIN_FAIL.msg());
        }

        JSONObject jsonObject =new JSONObject();
        jsonObject.put("userName",temp.getUserName());
        jsonObject.put("nickName",temp.getNickName());

        String jsonString = jsonObject.toJSONString();

        //如果成功则返回用户token
        String userToken = JwtUtil.createJWT(jsonString);

        //将当前用户存入Redis
        cache.setCacheObject(temp.getUserName(), new LoginUser(temp,ip));

        //返回一个携带payload的token
        return new ResponseResult<>(LiteHttpExceptionStatus.LOGIN_OK.code(), LiteHttpExceptionStatus.LOGIN_OK.msg(), new Token(userToken));
    }

    @Override
    public ResponseResult<String> register(User user) {

        String userName = user.getUserName();

        //根据表单输入的用户名与密码进行验证
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        User temp = mapper.selectOne(queryWrapper);//查询用户表

        //如果查询出来用户不为空
        if (!Objects.isNull(temp)) {
            return new ResponseResult<>(LiteHttpExceptionStatus.USER_ALREADY_EXIST.code(), LiteHttpExceptionStatus.USER_ALREADY_EXIST.msg());
        }

        //用户不存在则创建用户
        user.setPassword(PasswordEncoder.enCode(user.getPassword()));
        int count = mapper.insert(user);


        //执行成功影响条数
        if (count == 0) {
            return new ResponseResult<>(LiteHttpExceptionStatus.REGISTER_FAIL.code(), LiteHttpExceptionStatus.REGISTER_FAIL.msg());
        }

        return new ResponseResult<>(LiteHttpExceptionStatus.REGISTER_OK.code(), LiteHttpExceptionStatus.REGISTER_OK.msg(), null);
    }

    @Override
    public ResponseResult<Boolean> logout(HttpServletRequest request) {

        String token = String.valueOf(request.getHeader(Token.TokenFlag));//获取请求头中的token

        Boolean result = false;

        try {

            Claims claims = JwtUtil.parseJWT(token);//尝试解析token
            String payload = claims.getSubject();


            User user = JSON.parseObject(payload,User.class);

            if (Objects.isNull(user)){
                return ResponseUtils.getWrongResponseResult(LiteHttpExceptionStatus.LOGIN_FAIL.msg());
            }

            LoginUser loginUser = cache.getCacheObject(user.getUserName());

            if (!Objects.isNull(loginUser)) {//如果缓存中的用户存在
                result = cache.deleteObject(user.getUserName());//删除缓存中对应的用户
            }

        } catch (Exception e) {//解析失败表明token非法或者已经过期
            e.printStackTrace();
            throw new RuntimeException(LiteHttpExceptionStatus.NO_AUTH.msg());
        }

        //根据是否删除成功判断返回值
        return new ResponseResult<>(
                result ?
                        LiteHttpExceptionStatus.LOGOUT_OK.code() : LiteHttpExceptionStatus.LOGOUT_FAIL.code(),
                result ?
                        LiteHttpExceptionStatus.LOGOUT_OK.msg() : LiteHttpExceptionStatus.LOGOUT_FAIL.msg(),
                result);
    }
}
