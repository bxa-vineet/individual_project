package com.jobportal.controllers;

import com.jobportal.dto.ApplicationResponseDto;
import com.jobportal.enums.ApplicationStatus;
import com.jobportal.services.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;


    @PostMapping("/apply/{jobId}")
//    @PreAuthorize("hasRole('USER')")
    public ApplicationResponseDto applyJob(
            @PathVariable Long jobId,
            @RequestParam("file") MultipartFile file,
          Authentication authentication
    ) throws IOException {

        String username = authentication.getName();

        return applicationService.applyJob(jobId, username, file);
    }


    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    public List<ApplicationResponseDto> myApplications(Authentication auth) {
        return applicationService.getUserApplications(auth.getName());
    }


    @GetMapping("/job/{jobId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public List<ApplicationResponseDto> getApplicants(
            @PathVariable Long jobId,
            Authentication auth
    ) {
        return applicationService.getApplicantsByJob(jobId, auth.getName());
    }


    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ApplicationResponseDto updateStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status
    ) {
        return applicationService.updateStatus(id, status);
    }
}