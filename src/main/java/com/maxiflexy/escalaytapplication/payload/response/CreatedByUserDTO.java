package com.maxiflexy.escalaytapplication.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatedByUserDTO {

    private String fullName;
    private String email;
    private String jobTitle;
    private String department;
    private String phoneNumber;
    private String pictureUrl;
    private String username;

}