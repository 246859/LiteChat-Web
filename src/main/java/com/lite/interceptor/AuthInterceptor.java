package com.lite.interceptor;

import com.lite.dto.Token;
import com.lite.utils.AuthUtils;
import com.lite.utils.LiteHttpExceptionStatus;
import com.lite.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    RedisCache cache;

    @Autowired
    AuthUtils authUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String token = request.getHeader(Token.TokenFlag);

        try {
            if (Objects.isNull(token)) {//请求头未携带token
                throw new RuntimeException(LiteHttpExceptionStatus.NO_AUTH.msg());
            }

            if (authUtils.authIsValidUser(token,cache,request)){//校验是否为合法用户
                throw new RuntimeException(LiteHttpExceptionStatus.NO_AUTH.msg());
            }

        }catch (Exception e){
            response.sendError(HttpStatus.FORBIDDEN.value());
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
