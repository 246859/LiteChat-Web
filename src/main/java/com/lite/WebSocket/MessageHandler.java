package com.lite.WebSocket;

import com.lite.WebSocket.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;


@Component
public class MessageHandler implements WebSocketHandler {

    @Autowired
    private MessageService messageService;
    /**
     * 建立链接
     * @param session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        messageService.afterConnected(session);
    }

    /**
     * 接收消息
     * @param session
     * @param message
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        messageService.onMessage(session,message);
    }

    /**
     * 发生错误
     * @param session
     * @param exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {

    }

    /**
     * 链接关闭
     * @param session
     * @param closeStatus
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        messageService.afterClosed(session,closeStatus);
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
