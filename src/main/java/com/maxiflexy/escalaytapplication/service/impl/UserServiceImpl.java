package com.maxiflexy.escalaytapplication.service.impl;

import com.maxiflexy.escalaytapplication.config.JwtService;
import com.maxiflexy.escalaytapplication.entity.model.JwtToken;
import com.maxiflexy.escalaytapplication.entity.model.User;
import com.maxiflexy.escalaytapplication.exception.UserNotFoundException;
import com.maxiflexy.escalaytapplication.payload.request.LoginRequestDto;
import com.maxiflexy.escalaytapplication.payload.response.AdminUserDetailsDto;
import com.maxiflexy.escalaytapplication.payload.response.EmailDetails;
import com.maxiflexy.escalaytapplication.payload.response.LoginInfo;
import com.maxiflexy.escalaytapplication.payload.response.LoginResponse;
import com.maxiflexy.escalaytapplication.repository.JwtTokenRepository;
import com.maxiflexy.escalaytapplication.repository.UserRepository;
import com.maxiflexy.escalaytapplication.service.EmailService;
import com.maxiflexy.escalaytapplication.service.UserService;
import com.maxiflexy.escalaytapplication.utils.ForgetPasswordEmailBody;
import com.maxiflexy.escalaytapplication.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final JwtTokenRepository jwtTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Value("${baseUrl}")
    private String baseUrl;

    @Override
    public LoginResponse loginUser(LoginRequestDto loginRequestDto) {


        try{


            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getUsername(),
                            loginRequestDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findByUsername(loginRequestDto.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found with username: " + loginRequestDto.getUsername()));

            var jwtToken = jwtService.generateToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);
            System.out.println("Logged In!!!!");

            return LoginResponse.builder()
                    .responseCode("002")
                    .responseMessage("Login Successfully")
                    .loginInfo(LoginInfo.builder()
                            .username(user.getUsername())
                            .token(jwtToken)
                            .build())
                    .build();

        }catch (AuthenticationException e) {
            throw new CustomException("Invalid username or password!!", e);
        }

    }

    private void saveUserToken(User userModel, String jwtToken) {
        var token = JwtToken.builder()
                .user(userModel)
                .token(jwtToken)
                .tokenType("BEARER")
                .expired(false)
                .revoked(false)
                .build();
        jwtTokenRepository.save(token);
    }

    private void revokeAllUserTokens(User userModel) {
        var validUserTokens = jwtTokenRepository.findAllValidTokenByUser(userModel.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        jwtTokenRepository.saveAll(validUserTokens);
    }

    public String forgotPassword(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setTokenCreationDate(LocalDateTime.now());
        userRepository.save(user);

        String resetUrl = "http://localhost:5173/reset-password?token=" + token;

        // click this link to reset password;
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("FORGET PASSWORD")
                .messageBody(ForgetPasswordEmailBody.buildUserEmail(user.getFullName(), resetUrl ))
                .build();

        //send the reset password link
        emailService.mimeMailMessage(emailDetails);

        return "A reset password link has been sent to your account." + resetUrl;
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findByResetToken(String token) {
        return userRepository.findByResetToken(token).orElse(null);
    }

    public void updatePassword(String currentUsername, String newPassword) {

        // GET ADMIN ID BY USERNAME
        Optional<User> currentUser = userRepository.findByUsername(currentUsername);

        // Check if admin is present
        if (currentUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = currentUser.get();

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

    }

    @Override
    public String createToken(User user) {

        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return jwtToken;
    }


    // get user detail
    @Override
    public AdminUserDetailsDto getUserDetails(String username) {

        User user = userRepository.findByUsername(username).orElse(null);

        if(user == null){
            throw new UserNotFoundException("user not found");
        }
        System.out.println(user.getEmail() + user.getFullName());
        return AdminUserDetailsDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .email(user.getEmail())
                .department(user.getEmployeeDepartment().getDepartment())
                .jobTitle(user.getJobTitle())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }


    // edit user details
    @Override
    public AdminUserDetailsDto editUserInfo(String username, AdminUserDetailsDto adminUserDetailsDto) {

        User user = userRepository.findByUsername(username).orElse(null);

        if(user == null){
            throw new UserNotFoundException("user does not exist!");
        }

        user.setEmail(adminUserDetailsDto.getEmail());
        user.setPhoneNumber(adminUserDetailsDto.getPhoneNumber());
        user.setFullName(adminUserDetailsDto.getFullName());
        //user.setUsername(adminUserDetailsDto.getUsername());

        User newUser = userRepository.save(user);

        return AdminUserDetailsDto.builder()
                .fullName(newUser.getFullName())
                .phoneNumber(newUser.getPhoneNumber())
                .pictureUrl(newUser.getPictureUrl())
                .email(newUser.getEmail())
                .build();

    }
}



