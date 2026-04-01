package com.jobportal.dto;

import com.jobportal.enums.ApplicationStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationResponseDto {

    private Long id;
    private String jobTitle;
    private String companyName;
    private ApplicationStatus status;
    private String resumeFilePath;
}