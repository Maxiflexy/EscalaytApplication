package com.maxiflexy.escalaytapplication.service;

import com.maxiflexy.escalaytapplication.entity.model.User;
import com.maxiflexy.escalaytapplication.payload.request.LoginRequestDto;
import com.maxiflexy.escalaytapplication.payload.response.AdminUserDetailsDto;
import com.maxiflexy.escalaytapplication.payload.response.LoginResponse;

public interface UserService {
    LoginResponse loginUser(LoginRequestDto loginRequestDto);

    String forgotPassword(String email);

    boolean existsByEmail(String email);
    User findByResetToken(String token);
    void updatePassword(String username, String newPassword);

    String createToken(User user);

    AdminUserDetailsDto getUserDetails(String username);

    AdminUserDetailsDto editUserInfo(String username, AdminUserDetailsDto adminUserDetailsDto);

}