package com.maxiflexy.escalaytapplication.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssigneeDTO {

    private String fullName;
    private String email;
    private String jobTitle;
    private String phoneNumber;

    public AssigneeDTO(String fullName, String jobTitle) {
        this.fullName = fullName;

        this.jobTitle = jobTitle;
    }
}
