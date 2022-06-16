package com.lite;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lite.dao.authDao.AuthMapper;
import com.lite.dao.chatDao.ChatMapper;
import com.lite.entity.auth.User;
import com.lite.service.chat.ChatService;
import com.lite.utils.ChatUtils;
import com.lite.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.script.DigestUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

@Slf4j
@SpringBootTest
class LiteChatWebApplicationTests {

    @Autowired
    RedisCache redisCache;

    @Autowired
    AuthMapper mapper;

    @Autowired
    ChatMapper chatMapper;

    @Autowired
    ChatService chatService;
    @Test
    void contextLoads() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,"stranger");

        User temp = mapper.selectOne(queryWrapper);//查询用户表

        System.out.println(temp.toString());
        temp.setUserName("aaaa");
        temp.setEid(2);
        mapper.insert(temp);
    }

    @Test
    void test(){
        System.out.println(DigestUtils.sha1DigestAsHex("Lite_Chat_Serve"));
    }


    @Test
    void test1(){
        File file = new File("src/main/resources/static/avator/boy_avatar_1.svg");
        System.out.println(file.exists());
        System.out.println(file.length());
    }

    @Test
    void test2(){
        chatService.createGroup("stranger","半岛小镇");
    }

    @Test
    void test3(){
    }

    @Test
    void test4(){
//        System.out.println(chatService.getGroupMessage("61a3579f-39c8-4fc8-b4ed-f6e5ccd520f3", 0));
        System.out.println(chatService.getPrivateMessage("stranger","hsy",0));
    }

}
