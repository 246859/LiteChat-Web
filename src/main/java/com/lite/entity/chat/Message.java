package com.lite.entity.chat;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    /**
     * 发送者
     */
    private String sender;

    /**
     * 发送者的昵称
     */
    private String senderNickname;

    /**
     * 发送者昵称
     */
    private String conversationName;

    /**
     * 接收者
     */
    private String receiver;

    /**
     * 是否为群聊消息
     */
    private Boolean isGroup;

    /**
     * 群聊ID
     */
    private String groupId;
    /**
     * 发送的消息
     */
    private String message;

    /**
     * 消息类型
     */
    private Integer code;

    private String avatar;

    private String messageType;

    private String sendTime;

    private String fileName;

    public Message(String message){
        this.message = message;
    }

}
