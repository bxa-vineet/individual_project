package com.jobportal.repository;

import com.jobportal.entity.Companies;
import com.jobportal.entity.Jobs;
import com.jobportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompaniesRepository extends JpaRepository<Companies, Long> {

    List<Companies> findByOwner(User user);

}