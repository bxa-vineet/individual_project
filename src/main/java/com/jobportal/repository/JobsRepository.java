package com.jobportal.repository;

import com.jobportal.entity.Companies;
import com.jobportal.entity.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface JobsRepository extends JpaRepository<Jobs, Long>, JpaSpecificationExecutor<Jobs> {

    List<Jobs> findByTitleContainingIgnoreCase(String title);
    List<Jobs> findByCompanyIn(List<Companies> companies);


}