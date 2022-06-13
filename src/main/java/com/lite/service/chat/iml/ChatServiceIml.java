package com.lite.service.chat.iml;

import com.lite.dao.authDao.AuthMapper;
import com.lite.dao.chatDao.ChatMapper;
import com.lite.dto.ResponseResult;
import com.lite.entity.auth.User;
import com.lite.entity.chat.Friend;
import com.lite.entity.chat.Group;
import com.lite.entity.chat.Member;
import com.lite.service.chat.ChatService;
import com.lite.utils.LiteHttpExceptionStatus;
import com.lite.utils.RedisCache;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ChatServiceIml implements ChatService {

    @Autowired
    private RedisCache cache;

    @Autowired
    private ChatMapper chatMapper;

    @Autowired
    private AuthMapper authMapper;

    /**
     * 获取用户好友列表
     *
     * @param userName
     * @return
     */
    @Override
    public ResponseResult<List<Friend>> getFriendList(String userName) {
        ResponseResult<List<Friend>> responseResult = new ResponseResult<>();
        List<Friend> list = new ArrayList<>();

        if (Strings.isBlank(userName)) {
            responseResult.setCode(LiteHttpExceptionStatus.BAD_ARGS.code());
            responseResult.setData(list);
            responseResult.setMsg(LiteHttpExceptionStatus.BAD_ARGS.msg());
            responseResult.setIsSuccess(false);
            return responseResult;
        }
        //根据用户名查询好友列表
        list.addAll(chatMapper.getFriendList(userName));


        responseResult.setIsSuccess(true);
        responseResult.setData(list);
        responseResult.setCode(HttpStatus.OK.value());

        return responseResult;
    }

    /**
     * 获取用户群聊列表
     *
     * @param userName
     * @return
     */
    @Override
    public ResponseResult<List<Group>> getGroupList(String userName) {

        ResponseResult<List<Group>> responseResult = new ResponseResult<>();
        List<Group> list = new ArrayList<>();

        if (Strings.isBlank(userName)) {
            responseResult.setIsSuccess(false);
            responseResult.setCode(LiteHttpExceptionStatus.BAD_ARGS.code());
            responseResult.setMsg(LiteHttpExceptionStatus.BAD_ARGS.msg());

            return responseResult;
        }

        list.addAll(chatMapper.getGroupList(userName));


        responseResult.setIsSuccess(true);
        responseResult.setData(list);
        responseResult.setCode(LiteHttpExceptionStatus.OK.code());

        return responseResult;
    }

    /**
     * 获取一个好友
     *
     * @param userName
     * @param friendName
     * @return
     */
    @Override
    public ResponseResult<Friend> getFriend(String userName, String friendName) {
        //查询用户好友
        Friend friend = chatMapper.getFriend(userName, friendName);

        //包装
        ResponseResult<Friend> responseResult = new ResponseResult<>();
        responseResult.setData(friend);
        responseResult.setIsSuccess(true);
        responseResult.setCode(HttpStatus.OK.value());

        return responseResult;
    }

    /**
     * 获取一个群聊
     *
     * @param groupId
     * @return
     */
    @Override
    public ResponseResult<Group> getGroup(String groupId) {

        ResponseResult<Group> responseResult = new ResponseResult<>();

        if (Strings.isBlank(groupId)) {
            responseResult.setMsg(LiteHttpExceptionStatus.BAD_ARGS.msg());
            responseResult.setCode(LiteHttpExceptionStatus.BAD_ARGS.code());
            responseResult.setIsSuccess(false);
            return responseResult;
        }

        Group group = chatMapper.getGroup(groupId);

        responseResult.setData(group);
        responseResult.setCode(LiteHttpExceptionStatus.OK.code());
        responseResult.setMsg(LiteHttpExceptionStatus.OK.msg());
        responseResult.setIsSuccess(true);

        return responseResult;
    }

    /**
     * 添加好友
     *
     * @param userName
     * @param friendUserName
     * @return
     */
    @Override
    public ResponseResult<Boolean> addFriend(String userName, String friendUserName) {

        ResponseResult<Boolean> responseResult = new ResponseResult<>();

        if (Strings.isBlank(userName) || Strings.isBlank(friendUserName)) {
            responseResult.setMsg(LiteHttpExceptionStatus.BAD_ARGS.msg());
            responseResult.setCode(LiteHttpExceptionStatus.BAD_ARGS.code());
            responseResult.setIsSuccess(false);
        }

        //查询用户与好友
        User user = chatMapper.getUserInfo(userName);

        User friend = chatMapper.getUserInfo(friendUserName);



        //如果两个User至少有一个为空,或者已经是好友关系
        if (Objects.isNull(user)
                || Objects.isNull(friend)
                || Objects.isNull(user.getEid())
                || Objects.isNull(friend.getEid())
                || !Objects.isNull(chatMapper.getFriend(userName,friendUserName))) {

            responseResult.setMsg("好友添加失败");
            responseResult.setIsSuccess(false);
            responseResult.setCode(LiteHttpExceptionStatus.BAD_REQUEST.code());

            return responseResult;
        }

        Integer impactCount = chatMapper.addFriend(user.getEid(), friend.getEid());

        if (impactCount == 0) {
            responseResult.setMsg("好友添加失败");
            responseResult.setIsSuccess(false);
            responseResult.setCode(LiteHttpExceptionStatus.BAD_REQUEST.code());

            return responseResult;
        }

        responseResult.setMsg("好友添加成功");
        responseResult.setIsSuccess(true);
        responseResult.setCode(LiteHttpExceptionStatus.OK.code());

        return responseResult;
    }

    /**
     * 添加群聊
     *
     * @param groupId
     * @param userName
     * @return
     */
    @Override
    public ResponseResult<Boolean> addGroup(String groupId, String userName) {

        ResponseResult<Boolean> responseResult = new ResponseResult<>();

        Group group = chatMapper.getGroup(groupId);

        User user = chatMapper.getUserInfo(userName);

        if (Objects.isNull(group) || Objects.isNull(user) || (chatMapper.addGroup(group.getEid(), user.getEid()) == 0)) {
            responseResult.setIsSuccess(false);
            responseResult.setCode(LiteHttpExceptionStatus.BAD_ARGS.code());
            responseResult.setMsg("群聊添加失败");

            return responseResult;
        }

        responseResult.setMsg("群聊添加成功");
        responseResult.setCode(LiteHttpExceptionStatus.OK.code());
        responseResult.setIsSuccess(true);

        return responseResult;
    }

    /**
     * 退出一个群聊
     *
     * @param groupId
     * @param userName
     * @return
     */
    @Override
    public ResponseResult<Boolean> quitGroup(String groupId, String userName) {

        ResponseResult<Boolean> responseResult = new ResponseResult<>();

        Group group = chatMapper.getGroup(groupId);

        User user = chatMapper.getUserInfo(userName);

        if (Objects.isNull(group) || Objects.isNull(user) || (chatMapper.quitGroup(group.getEid(), user.getEid()) == 0)) {
            responseResult.setIsSuccess(false);
            responseResult.setCode(LiteHttpExceptionStatus.BAD_ARGS.code());
            responseResult.setMsg("群聊退出失败");

            return responseResult;
        }

        responseResult.setMsg("群聊退出成功");
        responseResult.setCode(LiteHttpExceptionStatus.OK.code());
        responseResult.setIsSuccess(true);

        return responseResult;
    }

    /**
     * 删除好友
     *
     * @param userName
     * @param friendUserName
     * @return
     */
    @Override
    public ResponseResult<Boolean> deleteFriend(String userName, String friendUserName) {

        ResponseResult<Boolean> responseResult = new ResponseResult<>();

        Integer impactCount = chatMapper.deleteFriend(userName, friendUserName);

        if (impactCount == 0) {
            responseResult.setIsSuccess(false);
            responseResult.setCode(LiteHttpExceptionStatus.BAD_REQUEST.code());
            responseResult.setMsg("删除好友失败");

            return responseResult;
        }

        responseResult.setMsg("删除好友成功");
        responseResult.setCode(LiteHttpExceptionStatus.OK.code());
        responseResult.setIsSuccess(true);

        return responseResult;
    }

    /**
     * 解散群聊
     *
     * @param userName
     * @param groupId
     * @return
     */
    @Override
    public ResponseResult<Boolean> deleteGroup(String userName, String groupId) {

        ResponseResult<Boolean> responseResult = new ResponseResult<>();

        if (Objects.isNull(cache.getCacheObject(userName)) ||
                Objects.isNull(chatMapper.getUserInfo(userName)) ||
                (chatMapper.deleteGroup(groupId) == 0)) {

            responseResult.setIsSuccess(false);
            responseResult.setCode(LiteHttpExceptionStatus.BAD_REQUEST.code());
            responseResult.setMsg("群聊解散失败");

            return responseResult;
        }

        responseResult.setMsg("群聊解散成功");
        responseResult.setCode(LiteHttpExceptionStatus.OK.code());
        responseResult.setIsSuccess(true);

        return responseResult;
    }

    /**
     * 获取群成员列表
     *
     * @param groupId
     * @return
     */
    @Override
    public ResponseResult<List<Member>> getGroupMembers(String groupId) {

        ResponseResult<List<Member>> responseResult = new ResponseResult<>();
        List<Member> list = new ArrayList<>();

        if (Objects.isNull(chatMapper.getGroup(groupId))){
            responseResult.setIsSuccess(false);
            responseResult.setData(list);
            responseResult.setMsg("获取群员列表失败");
            responseResult.setCode(LiteHttpExceptionStatus.BAD_REQUEST.code());

            return responseResult;
        }

        list.addAll(chatMapper.getGroupMember(groupId));

        responseResult.setCode(LiteHttpExceptionStatus.OK.code());
        responseResult.setData(list);
        responseResult.setMsg("获取群员列表成功");
        responseResult.setIsSuccess(true);

        return responseResult;
    }


    /**
     * 获取一个用户的信息
     *
     * @param userName
     * @return
     */
    @Override
    public ResponseResult<User> getUserInfo(String userName) {

        ResponseResult<User> responseResult = new ResponseResult<>();

        User user = chatMapper.getUserInfo(userName);

        if (Objects.isNull(user)){
            responseResult.setIsSuccess(false);
            responseResult.setCode(LiteHttpExceptionStatus.BAD_REQUEST.code());
            responseResult.setMsg("获取用户信息失败");

            return responseResult;
        }

        responseResult.setData(user);
        responseResult.setIsSuccess(true);
        responseResult.setCode(LiteHttpExceptionStatus.OK.code());
        responseResult.setMsg("获取用户信息成功");

        return responseResult;
    }
}
