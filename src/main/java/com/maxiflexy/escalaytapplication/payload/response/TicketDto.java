package com.maxiflexy.escalaytapplication.payload.response;

import com.maxiflexy.escalaytapplication.entity.enums.Priority;
import com.maxiflexy.escalaytapplication.entity.enums.Status;
import com.maxiflexy.escalaytapplication.entity.model.TicketComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String title;
    private String location;
    private Priority priority;
    private String description;
    private String createdByUser;
    private String createdByAdmin;
    private String resolvedByUser;
    private String resolvedByAdmin;
    private String assignedByAdmin;
    private Long createdUnder;
    private Status status;
    private int rating;
    private String review;
    private List<TicketComment> ticketComments;
    private String assignee;
}
