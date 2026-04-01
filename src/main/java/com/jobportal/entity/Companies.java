package com.jobportal.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "companies_details")
public class Companies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String location;
    @OneToMany(mappedBy = "company")
    @JsonIgnore
    private List<Jobs> jobs;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;





}
