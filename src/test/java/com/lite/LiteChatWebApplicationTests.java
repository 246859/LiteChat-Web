package com.lite;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lite.dao.authDao.AuthMapper;
import com.lite.entity.User;
import com.lite.utils.AvatarUtils;
import com.lite.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.script.DigestUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@SpringBootTest
class LiteChatWebApplicationTests {

    @Autowired
    RedisCache redisCache;

    @Autowired
    AuthMapper mapper;
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
        System.out.println(AvatarUtils.getDefaultAvatar());
    }
}
