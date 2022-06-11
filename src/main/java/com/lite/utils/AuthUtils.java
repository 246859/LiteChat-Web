package com.lite.utils;

import com.lite.entity.LoginUser;
import io.jsonwebtoken.Claims;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
public class AuthUtils {
    public LoginUser authIsValidUser(String token, RedisCache cache) throws RuntimeException {
        //尝试解析Token
        String userId = null;
        LoginUser loginUser = null;

        userId = parseJWT(token);

        //在Redis中查询对应userID的用户
        if (Objects.isNull(userId) || Objects.isNull((loginUser = cache.getCacheObject(userId)))) {
            return null;
        }

        return loginUser;
    }

    public boolean authIsValidUser(String token, RedisCache cache, HttpServletRequest request) {//验证HTTP请求的ip地址是否与当前登陆用户一致
        LoginUser loginUser = null;

        if (Objects.isNull(loginUser = authIsValidUser(token, cache))) {//用户是否已登陆 以及 token是否合法
            throw new RuntimeException(LiteHttpExceptionStatus.NO_AUTH.msg());
        }

        String requestIp = request.getRemoteAddr();

        if (!loginUser.getIp().equals(requestIp)) {//验证IP地址是否一致
            throw new RuntimeException(LiteHttpExceptionStatus.IP_ERROR.msg());
        }

        return true;
    }

    public boolean authIsValidUser(String token, RedisCache cache, ServerHttpRequest request) {//验证ws地址

        LoginUser loginUser = null;

        if (Objects.isNull(loginUser = authIsValidUser(token, cache))) {//用户是否已登陆 以及 token是否合法
            throw new RuntimeException(LiteHttpExceptionStatus.NO_AUTH.msg());
        }

        String requestWsIp = request.getRemoteAddress().getHostString();

        String wsAddress = loginUser.getWsAddress();


        //redis中的ws地址不为空,说明ws连接已建立,需要进行校验
        if (!Objects.isNull(wsAddress)) {
            if (!wsAddress.equals(requestWsIp)) {
                throw new RuntimeException(LiteHttpExceptionStatus.WS_ERROR.msg());
            }
        } else { //redis中的ws地址为空说明还未建立ws连接,可以直接放行,并存入此次的ws地址
            loginUser.setWsAddress(requestWsIp);
            String username = loginUser.getUser().getUserName();
            cache.setCacheObject(username,loginUser);
        }

        return true;
    }

    public String parseJWT(String token){
        try {
            Claims claims = JwtUtil.parseJWT(token);
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

}
