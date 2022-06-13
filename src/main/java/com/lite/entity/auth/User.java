package com.lite.entity.auth;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.omg.CORBA.IDLType;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@TableName("user")
public class User {

    @TableId(type = IdType.AUTO)//自增
    private Integer eid;

    private Integer gender;

    @TableField("user_name")
    private String userName;

    @TableField("nick_name")
    private String nickName;

    private String password;

    private String avatar;

    private String description;

    private Timestamp create_time;

    private Timestamp update_time;

    public User(Integer eid,String userName, String nickName, String password) {
        this.eid = eid;
        this.userName = userName;
        this.nickName = nickName;
        this.password = password;
    }
    public User(){

    }
}
