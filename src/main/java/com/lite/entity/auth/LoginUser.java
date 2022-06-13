package com.lite.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser {
    User user;

    String ip;

    String wsAddress;

    public LoginUser(User user, String ip) {
        this.user = user;
        this.ip = ip;
    }
}
