package com.jobportal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


//@Data
//@NoArgsConstructor
//@Entity
//@Table(name = "job_details")
//public class Jobs {
////    @Id
////   @GeneratedValue(strategy = GenerationType.IDENTITY)
////    private Long id;
////    private String title;
////    private String description;
////    private String location;
////    private double salary;
////    private Long company_id;
////    private String created_at;
//@Id
//@GeneratedValue(strategy = GenerationType.IDENTITY)
//private Long id;
//
//    private String title;
//    private String description;
//    private String location;
//    private double salary;
//
//    // 🔥 Company relation
//    @ManyToOne
//    @JoinColumn(name = "company_id")
//    private Companies company;
//
//    private String createdAt;
//}
@Entity
@Table(name = "job_details")
@Data
@NoArgsConstructor
public class Jobs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String location;
    private double salary;
    private Integer experience;
    private Boolean isFresher;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Companies company;
}