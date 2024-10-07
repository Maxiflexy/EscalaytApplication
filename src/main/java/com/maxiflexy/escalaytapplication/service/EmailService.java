package com.maxiflexy.escalaytapplication.service;


import com.maxiflexy.escalaytapplication.payload.response.EmailDetails;
import jakarta.mail.MessagingException;

public interface EmailService {

    void sendEmailAlert(EmailDetails emailDetails);

    void sendSimpleMailMessage(EmailDetails message, String firstName, String lastName, String link) throws MessagingException;

    void mimeMailMessage(EmailDetails emailDetails);
}
