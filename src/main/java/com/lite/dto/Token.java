package com.lite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    public static final String TokenFlag = "94a2776e7bd6f611462bc4344e17773c65fc4c486401643b724d102a8936dff4";

    private String token;
}
