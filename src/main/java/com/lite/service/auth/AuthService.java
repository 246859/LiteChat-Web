package com.lite.service.auth;

import com.lite.dto.ResponseResult;
import com.lite.dto.Token;
import com.lite.entity.User;

public interface AuthService {
    ResponseResult<Token> login(User user);

    ResponseResult<String> register(User user);
}
