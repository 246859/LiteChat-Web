package com.lite;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lite.dao.authDao.LoginMapper;
import com.lite.entity.User;
import com.lite.utils.PasswordEncoder;
import com.lite.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.script.DigestUtils;

import javax.xml.crypto.Data;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@SpringBootTest
class LiteChatWebApplicationTests {

    @Autowired
    RedisCache redisCache;

    @Autowired
    LoginMapper mapper;
    @Test
    void contextLoads() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,"wyh");

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

}
