package com.jobportal.controllers;

import com.jobportal.dto.CompanyResponseDto;
import com.jobportal.entity.Companies;
import com.jobportal.services.CompanyService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;


    @PostMapping
    @PreAuthorize("hasRole('EMPLOYER')")
    public CompanyResponseDto createCompany(@RequestBody Companies company,
                                            Authentication auth) {

        return companyService.createCompany(company, auth.getName());
    }


    @GetMapping("/my")
    @PreAuthorize("hasRole('EMPLOYER')")
    public List<CompanyResponseDto> getMyCompanies(Authentication auth) {

        return companyService.getMyCompanies(auth.getName());
    }
}