package com.lite.WebSocket;

import com.lite.entity.chat.Member;
import com.lite.entity.chat.Message;
import com.lite.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
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
    public void unicast(TextMessage message, Message packMessage){
        //TODO 单播消息实现

        try {
            //消息广播时 不转发给自己
            for (Map.Entry<String,WebSocketSession> entry:WebSocketPool.entrySet()){

                String receiver = packMessage.getReceiver();
                String sender = packMessage.getSender();
                String userName = entry.getKey();

                if (userName.equals(receiver) && !userName.equals(sender) ){
                    log.info("来自 {} 的消息转发给了 {}",packMessage.getSender(),packMessage.getReceiver());
                    entry.getValue().sendMessage(message);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void broadcast(TextMessage message, Message packMessage, List<String> memberList){
        try {
            for (Map.Entry<String,WebSocketSession> entry:WebSocketPool.entrySet()){
                String sender = packMessage.getSender();
                String userName = entry.getKey();

                if (memberList.contains(userName) && !userName.equals(sender)){
                    entry.getValue().sendMessage(message);
                }
            }
        }catch (Exception e){
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
