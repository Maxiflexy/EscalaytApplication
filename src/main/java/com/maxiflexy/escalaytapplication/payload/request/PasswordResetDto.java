package com.maxiflexy.escalaytapplication.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetDto {

    private String email;
    private String newPassword;
    private String confirmPassword;

}
