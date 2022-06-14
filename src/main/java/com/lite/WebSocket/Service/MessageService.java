package com.lite.WebSocket.Service;

import com.alibaba.fastjson.JSONObject;
import com.lite.dto.WebSocket.WebSocketEvent;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;

public interface MessageService {
    void afterConnected(WebSocketSession session);

    void onMessage(WebSocketSession session, WebSocketMessage<?> message);

    void onError(WebSocketSession session, Throwable exception);

    void afterClosed(WebSocketSession session, CloseStatus closeStatus);

    void onChatMessage(String userName,WebSocketEvent event);
}
