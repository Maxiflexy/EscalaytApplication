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
public class TicketResponse {

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String title;
    private String location;
    private String priority;
    private String description;
    private Long createdUnder;
    private String status;
    private int rating;
    private String review;
    private Long ticketCategoryId;
    private String ticketCategoryName;
    private String assigneeFullName;


}
