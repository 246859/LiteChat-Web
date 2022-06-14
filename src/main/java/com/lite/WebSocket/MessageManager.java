package com.lite.WebSocket;

import com.lite.entity.chat.Message;
import com.lite.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessageManager {

    /**
     * socket pool
     */
    private ConcurrentHashMap<String, WebSocketSession> WebSocketPool = new ConcurrentHashMap<>();


    /**
     * 广播消息
     * @param message
     */
    public void broadcast(TextMessage message, Message packMessage){
        //TODO 广播消息实现

        try {
            //消息广播时 广播给自己与发送者
            for (Map.Entry<String,WebSocketSession> entry:WebSocketPool.entrySet()){
                if (entry.getKey().equals(packMessage.getReceiver()) || entry.getKey().equals(packMessage.getSender())){
                    entry.getValue().sendMessage(message);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    public int getWebSocketSessionCount(){
        return this.WebSocketPool.size();
    }

    public void addWebSocketSessionIntoPool(String key ,WebSocketSession val){
        this.WebSocketPool.put(key,val);
    }

    public void removeWebSocketSessionFromPool(String key){
        this.WebSocketPool.remove(key);
    }
}
