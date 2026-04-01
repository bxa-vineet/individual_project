package com.jobportal.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobResponseDto {
    private Long id;
    private String title;
    private String description;
    private String location;
    private double salary;
    private String companyName;
    private Integer experience;
    private Boolean isFresher;
}