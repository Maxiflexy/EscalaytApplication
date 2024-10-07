package com.maxiflexy.escalaytapplication.payload.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketCategoryInfo {

    private String name;

    private Long createdUnder;

    private LocalDateTime createdAt;



}
