package com.maxiflexy.escalaytapplication.payload.response;


import com.maxiflexy.escalaytapplication.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeneralTicketDto {

    private Long id;

    private String title;

    private Status status;

    private long minutesDifference;

    private AdminUserDetailsDto createdByUserDto;

    private AdminUserDetailsDto createdByAdmin;

    private  AdminUserDetailsDto assigneeDto;

    private AdminUserDetailsDto resolvedByAdminDto;

    private AdminUserDetailsDto resolvedByUserDto;

}
