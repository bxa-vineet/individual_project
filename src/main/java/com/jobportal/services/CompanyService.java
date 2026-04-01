package com.jobportal.services;

import com.jobportal.dto.CompanyResponseDto;
import com.jobportal.entity.Companies;
import com.jobportal.entity.User;
import com.jobportal.repository.CompaniesRepository;
import com.jobportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompaniesRepository companiesRepository;
    private final UserRepository userRepository;


    public CompanyResponseDto createCompany(Companies company, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        company.setOwner(user);

        Companies saved = companiesRepository.save(company);

        return mapToDto(saved);
    }


    public List<CompanyResponseDto> getMyCompanies(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return companiesRepository.findByOwner(user)
                .stream()
                .map(this::mapToDto)
                .toList();
    }


    private CompanyResponseDto mapToDto(Companies company) {
        return CompanyResponseDto.builder()
                .id(company.getId())
                .name(company.getName())
                .description(company.getDescription())
                .location(company.getLocation())
                .build();
    }
}