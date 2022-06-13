package com.lite.entity.chat;

import lombok.Data;

@Data
public class Member {

    private String userName;

    private String nickName;

    private String avatar;

    private String description;

    private Integer roleId;

    private String groupId;

    private String groupName;
}
