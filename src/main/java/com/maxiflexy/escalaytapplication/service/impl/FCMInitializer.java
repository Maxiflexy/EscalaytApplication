package com.maxiflexy.escalaytapplication.service.impl;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FCMInitializer {

    @Value("${app.firebase-configuration-file}")
    private String firebaseConfigPath;

    Logger logger = LoggerFactory.getLogger(FCMInitializer.class);

    @PostConstruct
    public void initialize() {
        try {
            // Load firebase.json from the classpath
            InputStream inputStream = new ClassPathResource(firebaseConfigPath).getInputStream();

            // Convert inputStream to String
            String firebaseConfig = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            // Replace placeholders with environment variables
            firebaseConfig = replacePlaceholders(firebaseConfig);

            // Initialize Firebase options using the updated config
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(firebaseConfig.getBytes(StandardCharsets.UTF_8))))
                    .build();

            // Initialize FirebaseApp if it is not already initialized
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                logger.info("Firebase application initialized");
            }
        } catch (IOException e) {
            logger.error("Error initializing Firebase: " + e.getMessage(), e);
        }
    }

    // Helper method to replace placeholders with actual environment variables
    private String replacePlaceholders(String config) {
        // Map of placeholder keys to corresponding environment variables
        Map<String, String> envVars = Stream.of(
                new AbstractMap.SimpleEntry<>("${FIREBASE_TYPE}", System.getenv("FIREBASE_TYPE")),
                new AbstractMap.SimpleEntry<>("${FIREBASE_PROJECT_ID}", System.getenv("FIREBASE_PROJECT_ID")),
                new AbstractMap.SimpleEntry<>("${FIREBASE_PRIVATE_KEY_ID}", System.getenv("FIREBASE_PRIVATE_KEY_ID")),
                new AbstractMap.SimpleEntry<>("${FIREBASE_PRIVATE_KEY}", System.getenv("FIREBASE_PRIVATE_KEY")),
                new AbstractMap.SimpleEntry<>("${FIREBASE_CLIENT_EMAIL}", System.getenv("FIREBASE_CLIENT_EMAIL")),
                new AbstractMap.SimpleEntry<>("${FIREBASE_CLIENT_ID}", System.getenv("FIREBASE_CLIENT_ID")),
                new AbstractMap.SimpleEntry<>("${FIREBASE_AUTH_URI}", System.getenv("FIREBASE_AUTH_URI")),
                new AbstractMap.SimpleEntry<>("${FIREBASE_TOKEN_URI}", System.getenv("FIREBASE_TOKEN_URI")),
                new AbstractMap.SimpleEntry<>("${FIREBASE_AUTH_PROVIDER_CERT_URL}", System.getenv("FIREBASE_AUTH_PROVIDER_CERT_URL")),
                new AbstractMap.SimpleEntry<>("${FIREBASE_CLIENT_CERT_URL}", System.getenv("FIREBASE_CLIENT_CERT_URL"))
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // Replace placeholders in the config with actual values
        for (Map.Entry<String, String> entry : envVars.entrySet()) {
            config = config.replace(entry.getKey(), entry.getValue());
        }

        return config;
    }
}
