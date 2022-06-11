package com.lite.WebSocket.Service;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public interface MessageService {
    void afterConnected(WebSocketSession session);

    void onMessage(WebSocketSession session, WebSocketMessage<?> message);

    void onError(WebSocketSession session, Throwable exception);

    void afterClosed(WebSocketSession session, CloseStatus closeStatus);
}
