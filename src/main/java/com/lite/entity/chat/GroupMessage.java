package com.lite.entity.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMessage {

    Integer groupEid;

    String groupMsg;

    Integer senderEid;

    String sendTime;

    String msgType;

}
