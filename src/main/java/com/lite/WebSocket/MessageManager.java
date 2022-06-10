package com.lite.WebSocket;

import com.lite.entity.Message;
import com.lite.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessageManager {

    /**
     * socket pool
     */
    private ConcurrentHashMap<String, WebSocketSession> WebSocketPool = new ConcurrentHashMap<>();

    @Autowired
    private RedisCache cache;

    public void unicast(Message message){
        //TODO 单播消息实现
    }

    public void broadcast(Message message){
        //TODO 广播消息实现
    }

    public int getWebSocketSessionCount(){
        return this.WebSocketPool.size();
    }

    public boolean addWebSocketSessionIntoPool(WebSocketSession webSocketSession){

        return false;
    }

    public boolean removeWebSocketSessionFromPool(WebSocketSession socketSession){
        return false;
    }
}
