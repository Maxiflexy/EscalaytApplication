package com.maxiflexy.escalaytapplication.service.impl;

import com.maxiflexy.escalaytapplication.entity.model.ConfirmationToken;
import com.maxiflexy.escalaytapplication.service.TokenValidationService;
import com.maxiflexy.escalaytapplication.entity.model.Admin;
import com.maxiflexy.escalaytapplication.repository.AdminRepository;
import com.maxiflexy.escalaytapplication.repository.ConfirmationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenValidationServiceImpl implements TokenValidationService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final AdminRepository adminRepository;

    @Override
    public String validateToken(String token) {

        Optional<ConfirmationToken> confirmationTokenOptional = confirmationTokenRepository.findByToken(token);
        if (confirmationTokenOptional.isEmpty()) {
            return "Invalid token";
        }

        ConfirmationToken confirmationToken = confirmationTokenOptional.get();

        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            return "Token has expired";
        }

        Admin admin = confirmationToken.getAdmin();
        admin.setEnabled(true);
        adminRepository.save(admin);

        confirmationTokenRepository.delete(confirmationToken); //delete the token after successful verification

        return "Email confirmed successfully";
    }
}
