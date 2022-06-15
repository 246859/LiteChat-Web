package com.lite.service.chat;

import com.lite.dto.ResponseResult;
import com.lite.entity.auth.User;
import com.lite.entity.chat.Friend;
import com.lite.entity.chat.Group;
import com.lite.entity.chat.Member;

import java.util.List;

public interface ChatService {

    ResponseResult<List<Friend>> getFriendList(String userName);

    ResponseResult<List<Group>> getGroupList( String userName);

    ResponseResult<Friend> getFriend( String userName,String friendName);

    ResponseResult<Group> getGroup( String groupId);

    ResponseResult<Boolean> addFriend( String userName, String friendUserName);

    ResponseResult<Boolean> addGroup( String groupId,String userName);

    ResponseResult<Boolean> createGroup(String userName,String groupName);
    ResponseResult<Boolean> quitGroup( String groupId,String userName);

    ResponseResult<Boolean> deleteFriend( String userName, String friendUserName);

    ResponseResult<Boolean> deleteGroup( String userName, String groupId);

    ResponseResult<List<Member>> getGroupMembers(String groupId);

    ResponseResult<User> getUserInfo(String userName);
}
