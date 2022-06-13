package com.lite.entity.chat;

import lombok.Data;

@Data
public class Group {

    private Integer eid;

    private String avatar;

    private String groupId;

    private String groupName;

    private String description;

    private Integer memberCount;

    private String creator;

    private String createTime;
}
