package com.lite.controller.chat;

import com.lite.dto.ResponseResult;
import com.lite.entity.auth.User;
import com.lite.entity.chat.Friend;
import com.lite.entity.chat.Group;
import com.lite.entity.chat.Member;
import com.lite.service.chat.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService service;



    @GetMapping("/getFriends")
    public ResponseResult<List<Friend>> getFriendList(@RequestParam String userName){
        return service.getFriendList(userName);
    }

    @GetMapping("/getGroups")
    public ResponseResult<List<Group>> getGroupList(@RequestParam String userName){
        //TODO 获取群聊列表
        return service.getGroupList(userName);
    }

    /**
     * 获取用户好友信息
     * @param userName
     * @param friendName
     * @return
     */
    @GetMapping("/getFriend")
    public ResponseResult<Friend> getFriend(@RequestParam String userName,@RequestParam String friendName){
        return service.getFriend(userName,friendName);
    }

    /**
     * 获取一个群聊信息
     * @param groupId
     * @return
     */
    @GetMapping("/getGroup")
    public ResponseResult<Group> getGroup(@RequestParam String groupId){
        //TODO 获取一个群聊
        return service.getGroup(groupId);
    }

    /**
     * 添加一个好友
     * @param userName
     * @param friendUserName
     * @return
     */
    @PostMapping("/addFriend")
    public ResponseResult<Boolean> addFriend(@RequestParam String userName,@RequestParam String friendUserName){
        return service.addFriend(userName,friendUserName);
    }

    /**
     * 添加一个群聊
     * @param groupId
     * @return
     */
    @PostMapping("/addGroup")
    public ResponseResult<Boolean> addGroup(@RequestParam String groupId,@RequestParam String userName){
        //TODO 添加一个群聊
        return service.addGroup(groupId,userName);
    }

    /**
     * 创建一个群聊
     * @param userName
     * @param groupName
     * @return
     */
    @PostMapping("/createGroup")
    public ResponseResult<Boolean> createGroup(@RequestParam String userName,@RequestParam String groupName){
        return service.createGroup(userName, groupName);
    }

    /**
     * 删除一个好友
     * @param userName
     * @param friendUserName
     * @return
     */
    @DeleteMapping("/deleteFriend")
    public ResponseResult<Boolean> deleteFriend(@RequestParam String userName,@RequestParam String friendUserName){
        //TODO 删除一个好友
        return service.deleteFriend(userName,friendUserName);
    }

    /**
     * 删除一个群聊
     * @param userName
     * @param groupId
     * @return
     */
    @DeleteMapping("/deleteGroup")
    public ResponseResult<Boolean> deleteGroup(@RequestParam String userName,@RequestParam String groupId){
        //TODO 删除一个群聊
        return service.deleteGroup(userName,groupId);
    }

    /**
     * 获取群聊成员列表
     * @param groupId
     * @return
     */

    @GetMapping("/getGroupMembers")
    public ResponseResult<List<Member>> getGroupMembers(@RequestParam String groupId){
        //TODO 获取一个群聊的成员列表
        return service.getGroupMembers(groupId);
    }

    /**
     * 获取用户信息
     * @param userName
     * @return
     */
    @GetMapping("/getUserInfo")
    public ResponseResult<User> getUserInfo(@RequestParam String userName){
        //TODO 获取一个用户的详细信息
        return service.getUserInfo(userName);
    }

}
