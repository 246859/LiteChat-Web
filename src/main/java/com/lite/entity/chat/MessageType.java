package com.lite.entity.chat;

public enum MessageType {
    Raw_Text("文本消息", 0),
    Img_Text("图片消息", 1),
    File_Text("文件消息", 2),
    Mix_Text("混合消息", 3);
    private final String msg;
    private final int code;

    MessageType(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public String msg(){
        return this.msg;
    }
}
