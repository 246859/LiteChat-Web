package com.lite.entity.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivateMessage {

    private Integer senderEid;

    private Integer receiverEid;

    private String chatMsg;

    private String sendTime;

    private String msgType;
}
