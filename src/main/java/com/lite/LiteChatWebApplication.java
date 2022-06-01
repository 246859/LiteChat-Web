package com.lite;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin
@MapperScan("com.lite.dao")
public class LiteChatWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiteChatWebApplication.class, args);
    }

}
