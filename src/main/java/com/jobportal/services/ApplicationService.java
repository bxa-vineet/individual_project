//package com.jobportal.services;
//
//import com.jobportal.dto.ApplicationResponseDto;
//import com.jobportal.entity.*;
//import com.jobportal.enums.ApplicationStatus;
//import com.jobportal.repository.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class ApplicationService {
//
//    private final ApplicationRepository applicationRepository;
//    private final JobsRepository jobsRepository;
//    private final UserRepository userRepository;
//
//    private final String UPLOAD_DIR = "uploads/resumes/";
//
//    // 🔹 Apply Job (file + url supported)
//    public ApplicationResponseDto applyJob(Long jobId,
//                                           String username,
//                                           MultipartFile file,
//                                           String resumeUrl) throws IOException {
//
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Jobs job = jobsRepository.findById(jobId)
//                .orElseThrow(() -> new RuntimeException("Job not found"));
//
//        // 🔥 Prevent duplicate apply
//        if (applicationRepository.findByUserAndJob(user, job).isPresent()) {
//            throw new RuntimeException("Already applied to this job");
//        }
//
//        Application application = new Application();
//        application.setUser(user);
//        application.setJob(job);
//        application.setStatus(ApplicationStatus.APPLIED);
//
//        // 🔥 File upload
//        if (file != null && !file.isEmpty()) {
//            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//            File dest = new File(UPLOAD_DIR + fileName);
//            dest.getParentFile().mkdirs();
//            file.transferTo(dest);
//
//            application.setResumeFilePath(UPLOAD_DIR + fileName);
//        }
//
//        // 🔥 URL
//        if (resumeUrl != null && !resumeUrl.isEmpty()) {
//            application.setResumeUrl(resumeUrl);
//        }
//
//        return mapToDto(applicationRepository.save(application));
//    }
//
//    // 🔹 Get My Applications
//    public List<ApplicationResponseDto> getUserApplications(String username) {
//        User user = userRepository.findByUsername(username).orElseThrow();
//
//        return applicationRepository.findByUser(user)
//                .stream()
//                .map(this::mapToDto)
//                .toList();
//    }
//
//    // 🔹 Employer → View applicants
//    public List<ApplicationResponseDto> getApplicantsByJob(Long jobId, String username) {
//
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Jobs job = jobsRepository.findById(jobId)
//                .orElseThrow(() -> new RuntimeException("Job not found"));
//
//        // 🔐 SECURITY CHECK (VERY IMPORTANT)
//        if (!job.getCompany().getOwner().getId().equals(user.getId())) {
//            throw new RuntimeException("Unauthorized access");
//        }
//
//        return applicationRepository.findByJob(job)
//                .stream()
//                .map(this::mapToDto)
//                .toList();
//    }
//    // 🔹 Update Status
//    public ApplicationResponseDto updateStatus(Long appId,
//                                               ApplicationStatus status) {
//
//        Application app = applicationRepository.findById(appId)
//                .orElseThrow(() -> new RuntimeException("Application not found"));
//
//        app.setStatus(status);
//
//        return mapToDto(applicationRepository.save(app));
//    }
//
//    private ApplicationResponseDto mapToDto(Application app) {
//        return ApplicationResponseDto.builder()
//                .id(app.getId())
//                .jobTitle(app.getJob().getTitle())
//                .companyName(app.getJob().getCompany().getName())
//                .status(app.getStatus())
//                .resumeUrl(app.getResumeUrl())
//                .resumeFilePath(app.getResumeFilePath())
//                .build();
//    }
//}
package com.jobportal.services;

import com.jobportal.dto.ApplicationResponseDto;
import com.jobportal.entity.*;
import com.jobportal.enums.ApplicationStatus;
import com.jobportal.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobsRepository jobsRepository;
    private final UserRepository userRepository;


    @Value("${file.upload-dir}")
    private String uploadDir;


    @PostConstruct
    public void init() {
        System.out.println("UPLOAD DIR: " + uploadDir);

        File folder = new File(uploadDir);
        if (!folder.exists()) {
            folder.mkdirs(); // create folder automatically
        }
    }


    public ApplicationResponseDto applyJob(Long jobId,
                                           String username,
                                           MultipartFile file) throws IOException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Jobs job = jobsRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));


        if (applicationRepository.findByUserAndJob(user, job).isPresent()) {
            throw new RuntimeException("Already applied to this job");
        }

        Application application = new Application();
        application.setUser(user);
        application.setJob(job);
        application.setStatus(ApplicationStatus.APPLIED);
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Resume file is required");
        }

        if (file != null && !file.isEmpty()) {

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            File destination = new File(uploadDir + fileName);

            System.out.println("Saving file to: " + destination.getAbsolutePath());

            file.transferTo(destination);


            String fileUrl = "http://localhost:8080/uploads/resumes/" + fileName;
            application.setResumeFilePath(fileUrl);
        }



        return mapToDto(applicationRepository.save(application));
    }


    public List<ApplicationResponseDto> getUserApplications(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow();

        return applicationRepository.findByUser(user)
                .stream()
                .map(this::mapToDto)
                .toList();
    }


    public List<ApplicationResponseDto> getApplicantsByJob(Long jobId, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Jobs job = jobsRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getCompany().getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        return applicationRepository.findByJob(job)
                .stream()
                .map(this::mapToDto)
                .toList();
    }


    public ApplicationResponseDto updateStatus(Long appId,
                                               ApplicationStatus status) {

        Application app = applicationRepository.findById(appId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus(status);

        return mapToDto(applicationRepository.save(app));
    }

    private ApplicationResponseDto mapToDto(Application app) {
        return ApplicationResponseDto.builder()
                .id(app.getId())
                .jobTitle(app.getJob().getTitle())
                .companyName(app.getJob().getCompany().getName())
                .status(app.getStatus())
                .resumeFilePath(app.getResumeFilePath())
                .build();
    }
}