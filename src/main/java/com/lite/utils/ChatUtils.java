package com.lite.utils;

import com.lite.entity.chat.Member;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ChatUtils {

    public static List<String> getUserNameList(List<Member> memberList){
        List<String> userNameList = new ArrayList<>();
        for (Member member:memberList){
            userNameList.add(member.getUserName());
        }
        return userNameList;
    }

    public static String getTimeFormatNow(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

}
