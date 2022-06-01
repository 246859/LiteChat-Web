package com.lite.service.auth.iml;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lite.dao.authDao.LoginMapper;
import com.lite.dto.ResponseResult;
import com.lite.dto.Token;
import com.lite.entity.User;
import com.lite.service.auth.AuthService;
import com.lite.utils.JwtUtil;
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
            return new ResponseResult<>(HttpStatus.BAD_REQUEST.value(),"用户名不存在或者密码错误",null);
        }

        //如果成功则返回用户token
        String userToken = JwtUtil.createJWT(temp.getUserName());

        //将当前用户存入Redis
        cache.setCacheObject(temp.getUserName(), temp);

        return new ResponseResult<>(HttpStatus.OK.value(),"用户登陆成功",new Token(userToken));
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
            return new ResponseResult<>(HttpStatus.BAD_REQUEST.value(),"当前用户名已被占用",null);
        }

        //用户不存在则创建用户
        user.setPassword(PasswordEncoder.enCode(user.getPassword()));
        int count = mapper.insert(user);


        //执行成功影响条数
        if (count == 0){
            return new ResponseResult<>(HttpStatus.BAD_REQUEST.value(),"用户注册失败",null);
        }

        return new ResponseResult<>(HttpStatus.OK.value(),"用户注册成功",null);
    }
}
