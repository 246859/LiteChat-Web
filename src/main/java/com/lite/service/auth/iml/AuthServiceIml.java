package com.lite.service.auth.iml;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lite.dao.authDao.LoginMapper;
import com.lite.dto.ResponseResult;
import com.lite.dto.Token;
import com.lite.entity.User;
import com.lite.service.auth.AuthService;
import com.lite.utils.JwtUtil;
import com.lite.utils.LiteHttpExceptionStatus;
import com.lite.utils.PasswordEncoder;
import com.lite.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthServiceIml implements AuthService {

    @Autowired
    LoginMapper mapper;

    @Autowired
    RedisCache cache;

    @Override
    public ResponseResult<Token> login(User user) {

        String userName = user.getUserName();
        String password = user.getPassword();

        //根据表单输入的用户名与密码进行验证
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        User temp = mapper.selectOne(queryWrapper);//查询用户表


        if (Objects.isNull(temp) || Objects.isNull(password) ||!PasswordEncoder.enCode(password).equals(temp.getPassword())) {//验证失败则返回错误代码
            throw new RuntimeException(LiteHttpExceptionStatus.LOGIN_FAIL.msg());
        }

        //如果成功则返回用户token
        String userToken = JwtUtil.createJWT(temp.getUserName());

        //将当前用户存入Redis
        cache.setCacheObject(temp.getUserName(), temp);

        return new ResponseResult<>(LiteHttpExceptionStatus.LOGIN_OK.code(),LiteHttpExceptionStatus.LOGIN_OK.msg(),new Token(userToken));
    }

    @Override
    public ResponseResult<String> register(User user) {

        String userName = user.getUserName();

        //根据表单输入的用户名与密码进行验证
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        User temp = mapper.selectOne(queryWrapper);//查询用户表

        //如果查询出来用户不为空
        if (!Objects.isNull(temp)){
            throw new RuntimeException(LiteHttpExceptionStatus.USER_ALREADY_EXIST.msg());
        }

        //用户不存在则创建用户
        user.setPassword(PasswordEncoder.enCode(user.getPassword()));
        int count = mapper.insert(user);


        //执行成功影响条数
        if (count == 0){
            throw new RuntimeException(LiteHttpExceptionStatus.REGISTER_FAIL.msg());
        }

        return new ResponseResult<>(LiteHttpExceptionStatus.REGISTER_OK.code(),LiteHttpExceptionStatus.REGISTER_OK.msg(),null);
    }
}
