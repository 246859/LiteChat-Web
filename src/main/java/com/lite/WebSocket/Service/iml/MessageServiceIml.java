package com.lite.WebSocket.Service.iml;

import com.alibaba.fastjson.JSON;
import com.lite.WebSocket.MessageManager;
import com.lite.WebSocket.Service.MessageService;
import com.lite.entity.LoginUser;
import com.lite.entity.Message;
import com.lite.utils.AuthUtils;
import com.lite.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Service
public class MessageServiceIml implements MessageService {

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private AuthUtils authUtils;

    @Autowired
    private RedisCache cache;

    @Override
    public void afterConnected(WebSocketSession session) {
        //将token解析成用户名
        String token = session.getUri().getQuery();
        String userName = authUtils.parseJWT(token);

        //将此session存入pool中
        if (!Objects.isNull(userName)){
            log.info("ws client is connected, remote address is : {}",session.getRemoteAddress().getHostString());
            messageManager.addWebSocketSessionIntoPool(userName,session);
        }
    }

    @Override
    public void onMessage(WebSocketSession session, WebSocketMessage<?> message) {

        try {
            //将token解析成用户名
            String token = session.getUri().getQuery();
            String userName = authUtils.parseJWT(token);

            //将此session存入pool中
            if (!Objects.isNull(userName)){


                //解析JSON格式的消息
                String jsonPayload = (String)message.getPayload();

                log.info("ws client:{} is sending message, message: {}",session.getRemoteAddress().getHostString(),jsonPayload);

                //转换为Message类
                Message temp = JSON.parseObject(jsonPayload,Message.class);

                //获取缓存的中登陆用户
                LoginUser loginUser = cache.getCacheObject(userName);

                //存入对应的数据
                temp.setSendNickName(loginUser.getUser().getNickName());
                temp.setSenderName(loginUser.getUser().getUserName());

                //转换为JSON格式的消息
                String jsonMessage = JSON.toJSONString(temp);

                //包装成TextMessage
                TextMessage textMessage = new TextMessage(jsonMessage);

                messageManager.broadcast(textMessage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onError(WebSocketSession session, Throwable exception) {

    }

    @Override
    public void afterClosed(WebSocketSession session, CloseStatus closeStatus) {
        String token = session.getUri().getQuery();
        String userName = authUtils.parseJWT(token);

        if (!Objects.isNull(userName)){
            log.info("ws client is closed , remote address is : {}",session.getRemoteAddress().getHostString());
            messageManager.removeWebSocketSessionFromPool(userName);
        }
    }
}
