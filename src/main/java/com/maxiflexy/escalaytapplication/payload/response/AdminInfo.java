package com.maxiflexy.escalaytapplication.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminInfo {
    private String userName;

    private String email;

    private String password;
}
