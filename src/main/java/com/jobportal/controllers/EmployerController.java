package com.jobportal.controllers;

import com.jobportal.dto.JobResponseDto;
import com.jobportal.services.EmployerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employer")
@RequiredArgsConstructor
public class EmployerController {

    private final EmployerService employerService;


    @GetMapping("/jobs")
    @PreAuthorize("hasRole('EMPLOYER')")
    public List<JobResponseDto> getMyJobs(Authentication auth) {
        return employerService.getMyJobs(auth.getName());
    }
}