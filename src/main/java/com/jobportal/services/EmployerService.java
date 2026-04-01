package com.jobportal.services;

import com.jobportal.dto.JobResponseDto;
import com.jobportal.entity.Companies;
import com.jobportal.entity.Jobs;
import com.jobportal.entity.User;
import com.jobportal.repository.CompaniesRepository;
import com.jobportal.repository.JobsRepository;
import com.jobportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployerService {

    private final UserRepository userRepository;
    private final CompaniesRepository companiesRepository;
    private final JobsRepository jobsRepository;


    public List<JobResponseDto> getMyJobs(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();

        List<Companies> companies = companiesRepository.findByOwner(user);

        return jobsRepository.findByCompanyIn(companies)
                .stream()
                .map(job -> JobResponseDto.builder()
                        .id(job.getId())
                        .title(job.getTitle())
                        .description(job.getDescription())
                        .location(job.getLocation())
                        .salary(job.getSalary())
                        .companyName(job.getCompany().getName())
                        .experience(job.getExperience())
                        .isFresher(job.getIsFresher())
                        .build())
                .toList();
    }
}