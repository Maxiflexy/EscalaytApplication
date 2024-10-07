package com.maxiflexy.escalaytapplication.payload.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketInfo {

    private String title;

    private Long createdUnder;

    private LocalDateTime createdAt;

    // New field for file URL
    private String fileUrl;
}

