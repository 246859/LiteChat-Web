package com.lite.WebSocket.Service.iml;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lite.WebSocket.MessageManager;
import com.lite.WebSocket.Service.MessageService;
import com.lite.dao.chatDao.ChatMapper;
import com.lite.dto.WebSocket.WebSocketEvent;
import com.lite.entity.auth.User;
import com.lite.entity.chat.Group;
import com.lite.entity.chat.Member;
import com.lite.entity.chat.Message;
import com.lite.service.chat.ChatService;
import com.lite.utils.AuthUtils;
import com.lite.utils.ChatUtils;
import com.lite.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
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

    @Autowired
    ChatService chatService;

    @Override
    public void afterConnected(WebSocketSession session) {
        //将token解析成用户名
        String token = session.getUri().getQuery();
        User user = JSONObject.parseObject(authUtils.parseJWT(token), User.class);


        //将此session存入pool中
        if (!Objects.isNull(user.getUserName())) {
            log.info("ws client is connected, remote address is : {}", session.getRemoteAddress().getHostString());
            messageManager.addWebSocketSessionIntoPool(user.getUserName(), session);
        }
    }

    @Override
    public void onMessage(WebSocketSession session, WebSocketMessage<?> message) {
        try {
            //将token解析成用户名
            String token = session.getUri().getQuery();
            String jwtPayload = authUtils.parseJWT(token);

            //将此session存入pool中
            if (!Objects.isNull(jwtPayload)) {

                String jsonPayload = (String) message.getPayload();

                WebSocketEvent wsEvent = JSON.parseObject(jsonPayload, WebSocketEvent.class);

                log.info("ws client:{} is sending message, message: {}", session.getRemoteAddress().getHostString(), jsonPayload);

                if (!Objects.isNull(wsEvent)) {

                    switch (wsEvent.getEvent()) {
                        case "CHAT_MESSAGE": {
                            onChatMessage(jwtPayload, wsEvent);
                        }
                        break;
                        case "INFO_MESSAGE": {

                        }
                        break;
                    }

                }

            }

        } catch (Exception e) {
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

        if (!Objects.isNull(userName)) {
            log.info("ws client is closed , remote address is : {}", session.getRemoteAddress().getHostString());
            messageManager.removeWebSocketSessionFromPool(userName);
        }
    }

    @Override
    public void onChatMessage(String jwtPayload, WebSocketEvent wsEvent) {

        try {

            //转换成user类
            User user = JSON.parseObject(jwtPayload, User.class);

            //转换为Message类
            Message message = JSON.toJavaObject((JSON) wsEvent.getPayload(), Message.class);
            //发送时间
            message.setSendTime(ChatUtils.getTimeFormatNow());

            //转换为JSON格式的消息


            if (!message.getIsGroup()) {//单播消息
                //包装成TextMessage
                wsEvent.setPayload(message);
                String jsonMessage = JSON.toJSONString(wsEvent);
                TextMessage textMessage = new TextMessage(jsonMessage);

                chatService.insertPrivateMsg(message);
                messageManager.unicast(textMessage, message);
            } else {//群聊广播消息

                String groupId = message.getGroupId();
                Group group = chatService.getGroup(groupId).getData();


                //重新写入会话名称
                message.setConversationName(group.getGroupName());
                message.setGroupId(groupId);

                wsEvent.setPayload(message);

                //包装成TextMessage
                String jsonMessage = JSON.toJSONString(wsEvent);
                TextMessage textMessage = new TextMessage(jsonMessage);

                //查询群聊成员
                List<Member> memberList = chatService.getGroupMembers(groupId).getData();
                List<String> userNameList = ChatUtils.getUserNameList(memberList);

                chatService.insertGroupMsg(message);
                messageManager.broadcast(textMessage, message, userNameList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
