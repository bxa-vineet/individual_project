package com.jobportal.dto;

import lombok.Data;

@Data
public class JobFilterRequest {

    private String keyword;
    private String companyName;
    private Integer minExperience;
    private Integer maxExperience;
    private Boolean fresher;
    private Double minSalary;
    private Double maxSalary;
}