package com.lite.interceptor;

import com.lite.utils.JwtUtil;
import com.lite.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    RedisCache cache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

        if (Objects.isNull(token)) {//请求头未携带token 返回401
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        //尝试解析Token,解析失败则返回401
        String userId = null;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        //在Redis中查询对应userID的用户,如果不存在则返回401
        if (Objects.isNull(userId) || Objects.isNull(cache.getCacheObject(userId))) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        //以上校验全部通过则确认为登陆状态
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
