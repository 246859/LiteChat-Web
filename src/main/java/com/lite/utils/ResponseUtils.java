package com.lite.utils;

import com.lite.dto.ResponseResult;

public class ResponseUtils {
    public static ResponseResult getWrongResponseResult(String msg) {

        ResponseResult<?> responseResult =new ResponseResult();

        responseResult.setIsSuccess(false);
        responseResult.setMsg(msg);
        responseResult.setCode(LiteHttpExceptionStatus.BAD_ARGS.code());

        return responseResult;
    }
}
