package com.lite.dto.WebSocket;

public enum WebSocketEvenType {

    CHAT_MESSAGE("CHAT_MESSAGE");

    private final String val;

    WebSocketEvenType(String val){
        this.val = val;
    }

    public String val(){
        return this.val;
    }
}
