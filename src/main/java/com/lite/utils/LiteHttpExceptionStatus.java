package com.lite.utils;

public enum LiteHttpExceptionStatus {

    BAD_REQUEST("请求异常,发生错误", 400),
    NO_AUTH("用户未经过认证", 400),
    ILLEGAL_AUTH("非法的用户认证", 400),
    USER_NOT_FOUND("用户不存在", 400),
    USER_ALREADY_EXIST("用户已存在", 400),
    LOGIN_OK("用户登陆成功", 200),
    LOGIN_FAIL("用户登陆失败,用户名或密码错误", 400),
    REGISTER_OK("用户注册成功", 200),
    REGISTER_FAIL("用户注册成功", 400);


    private final String msg;

    private final Integer code;

    LiteHttpExceptionStatus(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public String msg() {
        return this.msg;
    }

    public Integer code() {
        return this.code;
    }
}
