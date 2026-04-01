package com.jobportal.controllers;

import com.jobportal.dto.JobFilterRequest;
import com.jobportal.dto.JobRequestDto;
import com.jobportal.dto.JobResponseDto;
import com.jobportal.entity.Jobs;
import com.jobportal.services.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;


    @PostMapping
    @PreAuthorize("hasRole('EMPLOYER')")
    public JobResponseDto createJob(@RequestBody JobRequestDto dto) {
        return jobService.createJob(dto);
    }


    @GetMapping
    public List<JobResponseDto> getAllJobs() {
        return jobService.getAllJobs();
    }


    @GetMapping("/{id}")
    public JobResponseDto getJob(@PathVariable Long id) {
        return jobService.getJobById(id);
    }


    @GetMapping("/search")
    public List<JobResponseDto> searchJobs(@RequestParam String keyword) {
        return jobService.searchJobs(keyword);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public JobResponseDto updateJob(@PathVariable Long id,
                                    @RequestBody JobRequestDto dto) {
        return jobService.updateJob(id, dto);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public void deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
    }
    @PostMapping("/filter")
    public List<JobResponseDto> filterJobs(@RequestBody JobFilterRequest request) {
        return jobService.filterJobs(request);
    }
}