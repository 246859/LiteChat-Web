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
     * 接收者
     */
    private String receiverName;

    /**
     * 是否为私聊消息
     */
    private String isPersonal;

    /**
     * 发送的消息
     */
    private String message;


}
