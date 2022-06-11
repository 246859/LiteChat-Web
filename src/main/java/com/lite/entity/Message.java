package com.lite.entity;


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
    private String senderName;

    /**
     * 发送者昵称
     */
    private String sendNickName;

    /**
     * 接收者
     */
    private String receiverName;

    /**
     * 是否为私聊消息
     */
    private Boolean isPersonal;

    /**
     * 发送的消息
     */
    private String message;

    /**
     * 消息类型
     */
    private MessageType type;

    public Message(String message){
        this.message = message;
    }

}
