package com.lite.config;

import com.lite.WebSocket.MessageHandler;
import com.lite.interceptor.MessageHandShakeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {


    @Autowired
    private MessageHandler messageHandler;

    @Autowired
    private MessageHandShakeInterceptor handShakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(messageHandler,"/Message")
                .addInterceptors(handShakeInterceptor)
                .setAllowedOrigins("*");
    }
}
