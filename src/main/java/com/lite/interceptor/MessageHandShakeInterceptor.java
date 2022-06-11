package com.lite.interceptor;

import com.lite.utils.AuthUtils;
import com.lite.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class MessageHandShakeInterceptor implements HandshakeInterceptor {

    @Autowired
    private RedisCache cache;

    @Autowired
    private AuthUtils utils;

    /**
     * 握手拦截器,在握手之前拦截,判断携带的用户token与ip地址是否合法
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        boolean flag = false;

        try {
            String token = request.getURI().getQuery();

            if (utils.authIsValidUser(token, cache, request)) {//过程中会校验ip地址,token是否存在,token是否合法
                flag = true;
            }

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return flag;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
