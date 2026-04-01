//package com.jobportal.services;
//
//import com.jobportal.dto.JobFilterRequest;
//import com.jobportal.dto.JobRequestDto;
//import com.jobportal.dto.JobResponseDto;
//import com.jobportal.entity.Companies;
//import com.jobportal.entity.Jobs;
//import com.jobportal.repository.CompaniesRepository;
//import com.jobportal.repository.JobsRepository;
//import com.jobportal.specification.JobSpecification;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class JobService {
//
//    private final JobsRepository jobsRepository;
//    private final CompaniesRepository companiesRepository;
//
//    // 🔹 Create Job
//    public JobResponseDto createJob(JobRequestDto dto) {
//        Companies company = companiesRepository.findById(dto.getCompanyId())
//                .orElseThrow(() -> new RuntimeException("Company not found"));
//
//        Jobs job = new Jobs();
//        job.setTitle(dto.getTitle());
//        job.setDescription(dto.getDescription());
//        job.setLocation(dto.getLocation());
//        job.setSalary(dto.getSalary());
//        job.setCompany(company);
//
//        Jobs saved = jobsRepository.save(job);
//
//        return mapToDto(saved);
//    }
//
//    // 🔹 Get All Jobs
//    public List<JobResponseDto> getAllJobs() {
//        return jobsRepository.findAll()
//                .stream()
//                .map(this::mapToDto)
//                .toList();
//    }
//
//    // 🔹 Get Job By ID
//    public JobResponseDto getJobById(Long id) {
//        Jobs job = jobsRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Job not found"));
//
//        return mapToDto(job);
//    }
//
//    // 🔹 Search Jobs
//    public List<JobResponseDto> searchJobs(String keyword) {
//        return jobsRepository.findByTitleContainingIgnoreCase(keyword)
//                .stream()
//                .map(this::mapToDto)
//                .toList();
//    }
//
//    // 🔹 Update Job
//    public JobResponseDto updateJob(Long id, JobRequestDto dto) {
//        Jobs job = jobsRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Job not found"));
//
//        job.setTitle(dto.getTitle());
//        job.setDescription(dto.getDescription());
//        job.setLocation(dto.getLocation());
//        job.setSalary(dto.getSalary());
//
//        return mapToDto(jobsRepository.save(job));
//    }
//
//    // 🔹 Delete Job
//    public void deleteJob(Long id) {
//        jobsRepository.deleteById(id);
//    }
//
//    // 🔹 Mapper
//    private JobResponseDto mapToDto(Jobs job) {
//        return JobResponseDto.builder()
//                .id(job.getId())
//                .title(job.getTitle())
//                .description(job.getDescription())
//                .location(job.getLocation())
//                .salary(job.getSalary())
//                .companyName(job.getCompany().getName())
//                .build();
//    }
//    // Filter Job
//    public List<Jobs> filterJobs(JobFilterRequest request) {
//
//        Specification<Jobs> spec = JobSpecification.filterJobs(
//                request.getKeyword(),
//                request.getCompanyName(),
//                request.getMinExperience(),
//                request.getMaxExperience(),
//                request.getFresher(),
//                request.getMinSalary(),
//                request.getMaxSalary()
//        );
//
//        return jobsRepository.findAll(spec);
//    }
//}


package com.jobportal.services;

import com.jobportal.dto.JobFilterRequest;
import com.jobportal.dto.JobRequestDto;
import com.jobportal.dto.JobResponseDto;
import com.jobportal.entity.Companies;
import com.jobportal.entity.Jobs;
import com.jobportal.repository.CompaniesRepository;
import com.jobportal.repository.JobsRepository;
import com.jobportal.specification.JobSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobsRepository jobsRepository;
    private final CompaniesRepository companiesRepository;


    public JobResponseDto createJob(JobRequestDto dto) {

        Companies company = companiesRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Jobs job = new Jobs();
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setLocation(dto.getLocation());
        job.setSalary(dto.getSalary());
        job.setExperience(dto.getExperience());
        job.setIsFresher(dto.getIsFresher());

        job.setCompany(company);

        Jobs saved = jobsRepository.save(job);

        return mapToDto(saved);
    }


    public List<JobResponseDto> getAllJobs() {
        return jobsRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }


    public JobResponseDto getJobById(Long id) {
        Jobs job = jobsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        return mapToDto(job);
    }


    public List<JobResponseDto> searchJobs(String keyword) {
        return jobsRepository.findByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToDto)
                .toList();
    }


    public JobResponseDto updateJob(Long id, JobRequestDto dto) {

        Jobs job = jobsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setLocation(dto.getLocation());
        job.setSalary(dto.getSalary());


        job.setExperience(dto.getExperience());
        job.setIsFresher(dto.getIsFresher());

        return mapToDto(jobsRepository.save(job));
    }


    public void deleteJob(Long id) {
        jobsRepository.deleteById(id);
    }


    public List<JobResponseDto> filterJobs(JobFilterRequest request) {

        Specification<Jobs> spec = JobSpecification.filterJobs(
                request.getKeyword(),
                request.getCompanyName(),
                request.getMinExperience(),
                request.getMaxExperience(),
                request.getFresher(),
                request.getMinSalary(),
                request.getMaxSalary()
        );

        return jobsRepository.findAll(spec)
                .stream()
                .map(this::mapToDto)
                .toList();
    }


    private JobResponseDto mapToDto(Jobs job) {
        return JobResponseDto.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .location(job.getLocation())
                .salary(job.getSalary())
                .companyName(job.getCompany().getName())
                .experience(job.getExperience())
                .isFresher(job.getIsFresher())
                .build();
    }
}