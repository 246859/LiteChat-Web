package com.lite.dao.chatDao;

import com.lite.entity.auth.User;
import com.lite.entity.chat.Friend;
import com.lite.entity.chat.Group;
import com.lite.entity.chat.Member;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ChatMapper {

    List<Friend> getFriendList(String userName);

    List<Group> getGroupList(String userName);

    Friend getFriend(String userName,String friendName);

    Group getGroup( String groupId);

    Integer addFriend(Integer userId, Integer friendId);

    Integer createGroup(Group group);

    Integer addGroup(Integer userEid,Integer groupEid,Integer roleId);

    Integer deleteFriend( String userName, String friendUserName);

    Integer deleteGroup(String groupId);

    Integer quitGroup(Integer groupEid,Integer userEid);

    List<Member> getGroupMember(String groupId);

    User getUserInfo(String userName);

    Member getMember(String userName,String groupId);
}
