package com.maxiflexy.escalaytapplication.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TicketRepliesDTO {

    private Long id;
    private String comment;
    private LocalDateTime createdAt;
    private String fullName;
    private String profileUrl;
}
