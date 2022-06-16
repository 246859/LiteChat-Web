package com.lite.config;

import com.lite.WebSocket.MessageHandler;
import com.lite.interceptor.MessageHandShakeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer, ServletContextInitializer {


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


    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {//设置websocket传输的大小限制
        servletContext.setInitParameter("org.apache.tomcat.websocket.textBufferSize","10240000");
        servletContext.setInitParameter("org.apache.tomcat.websocket.binaryBufferSize","10240000");
    }
}
