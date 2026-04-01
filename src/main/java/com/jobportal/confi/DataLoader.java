package com.jobportal.confi;

import com.jobportal.entity.Role;
import com.jobportal.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initRoles(AppUserRepository appUserRepository) {
        return args -> {
            if (appUserRepository.findByName("EMPLOYER").isEmpty()) appUserRepository.save(new Role("EMPLOYER"));
            if (appUserRepository.findByName("USER").isEmpty()) appUserRepository.save(new Role("USER"));
        };
    }
}
