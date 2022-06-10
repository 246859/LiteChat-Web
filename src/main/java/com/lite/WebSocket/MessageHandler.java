package com.lite.WebSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class MessageHandler implements WebSocketHandler {


    @Autowired
    private MessageManager messageManager;
    /**
     * 建立链接
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        messageManager.addWebSocketSessionIntoPool(session);
        System.out.println("连接建立");;
    }

    /**
     * 接收消息
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println("有消息发送过来了!");
        System.out.println(message.toString());
        System.out.println(message.getPayload().toString());
        System.out.println(session);
    }

    /**
     * 发生错误
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    /**
     * 链接关闭
     * @param session
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("连接已断开");
        System.out.println(session.toString());
        System.out.println(session.getAttributes().toString());
    }


    /**
     * 是否支持发送部分消息
     * @return
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
