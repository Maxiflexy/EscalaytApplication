package com.maxiflexy.escalaytapplication.payload.request;

import com.maxiflexy.escalaytapplication.entity.enums.Priority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequestDto {

    private String title;

    private String location;

    private Priority priority;

    private String description;

    // New field for the file
    private MultipartFile file;

    private String fileTitle;

}
