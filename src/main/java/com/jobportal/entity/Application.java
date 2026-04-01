package com.jobportal.entity;

import com.jobportal.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(name = "application_table")
public class Application {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private Long job_id;
//    private Long user_id;
//    private ApplicationStatus status;
//    String resume_url;
//    private String applied_id;
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "job_id")
    private Jobs job;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;


    private String resumeFilePath;

    private String appliedId;
}

