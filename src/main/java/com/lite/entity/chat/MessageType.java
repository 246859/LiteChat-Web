package com.lite.entity.chat;

public enum MessageType {
    Raw_Text("文本消息"),
    Img_Text("图片消息"),
    File_Text("文件消息"),
    Mix_Text("混合消息");
    private final String msg;

    MessageType(String msg) {

        this.msg = msg;
    }

    public String msg(){
        return this.msg;
    }
}
