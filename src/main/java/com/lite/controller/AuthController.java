package com.lite.controller;

import com.lite.dto.ResponseResult;
import com.lite.dto.Token;
import com.lite.entity.User;
import com.lite.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;
    @RequestMapping("/login")
    public ResponseResult<Token> login(@RequestBody User user){
        return authService.login(user);
    }

    @RequestMapping("/register")
    public ResponseResult<String> register(@RequestBody User user){
        return authService.register(user);
    }
}
