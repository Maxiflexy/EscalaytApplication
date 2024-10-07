package com.maxiflexy.escalaytapplication.payload.request;

import lombok.Data;

@Data
public class TicketRatingRequest {

    private int rating;
    private String review;
}
