package com.lite.entity.chat;

public enum GroupRole {

    CREATOR("创建者",1),
    MANAGER("管理员",2),
    MEMBER("普通成员",3),
    ;

    private final String roleMsg;

    private final Integer roleId;


    GroupRole(String roleMsg, Integer roleId) {
        this.roleMsg = roleMsg;
        this.roleId = roleId;
    }

    public String msg() {
        return roleMsg;
    }

    public Integer roleId() {
        return roleId;
    }
}
