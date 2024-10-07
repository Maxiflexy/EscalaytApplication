package com.maxiflexy.escalaytapplication.service.impl;

import com.maxiflexy.escalaytapplication.entity.model.NotificationToken;
import com.maxiflexy.escalaytapplication.repository.NotificationTokenRepository;
import com.maxiflexy.escalaytapplication.service.NotificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationTokenServiceImpl implements NotificationTokenService {
    @Autowired
    private NotificationTokenRepository tokenRepository;

//    @Override
//    public void saveToken(String token) {
//        NotificationToken existingToken = tokenRepository.findByToken(token);
//        if(existingToken == null){
//            NotificationToken newToken = new NotificationToken();
//            newToken.setToken(token);
//            tokenRepository.save(newToken);
//        }
//        else {
//            System.out.println("Token already exists: " + token);
//            // You can update the existing token if needed
//            // existingToken.setUpdatedAt(new Date());
//            // fcmTokenRepository.save(existingToken);
//        }
//    }
//
//
//    @Override
//    public void deleteToken(String token) {
//        tokenRepository.deleteByToken(token);
//    }

    @Override
    // Method to get all tokens by user ID
    public List<NotificationToken> getTokensByUserId(Long userId) {
        return tokenRepository.findByUserId(userId);
    }

    public boolean tokenExists(String token) {
        return tokenRepository.existsByToken(token);
    }

    @Override
    public NotificationToken saveToken(Long userId, String token) {
        NotificationToken notificationToken = new NotificationToken();
        notificationToken.setUserId(userId);
        notificationToken.setToken(token);
        return tokenRepository.save(notificationToken);
    }

    @Override
    public void deleteToken(Long userId, String token) {
        tokenRepository.deleteByUserIdAndToken(userId, token);
    }

}
