package com.lite.controller.auth;

import com.lite.dto.ResponseResult;
import com.lite.dto.Token;
import com.lite.entity.auth.User;
import com.lite.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private AuthService authService;

    @RequestMapping("/login")
    public ResponseResult<Token> login(@RequestBody User user) {
        return authService.login(user,request.getRemoteAddr());
    }

    @RequestMapping("/register")
    public ResponseResult<String> register(@RequestBody User user) {
        return authService.register(user);
    }

    @RequestMapping("/logout")
    public ResponseResult<Boolean> logout() {
        return authService.logout(request);
    }
}
