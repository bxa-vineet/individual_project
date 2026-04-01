package com.jobportal.repository;

import com.jobportal.entity.Application;
import com.jobportal.entity.Jobs;
import com.jobportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Optional<Application> findByUserAndJob(User user, Jobs job);

    List<Application> findByJob(Jobs job);

    List<Application> findByUser(User user);
}