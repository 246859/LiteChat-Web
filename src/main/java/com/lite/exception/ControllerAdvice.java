package com.lite.exception;

import com.lite.dto.ResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)//统一处理所有异常
    public ResponseResult<String> handlerException(Exception exception){
        exception.printStackTrace();//打印异常
        return new ResponseResult<>(HttpStatus.BAD_REQUEST.value(),exception.getMessage());
    }
}
