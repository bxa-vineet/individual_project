package com.jobportal.dto;

import lombok.Data;

@Data
public class JobRequestDto {
    private String title;
    private String description;
    private String location;
    private double salary;
    private Long companyId;
    private Integer experience;
    private Boolean isFresher;

}