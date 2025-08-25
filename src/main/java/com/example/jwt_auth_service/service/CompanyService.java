package com.example.jwt_auth_service.service;

import com.example.jwt_auth_service.dto.CompanyDTO;
import com.example.jwt_auth_service.exception.ResourceNotFoundException;
import com.example.jwt_auth_service.model.Company;
import com.example.jwt_auth_service.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(c -> new CompanyDTO(c.getId(), c.getCompanyName(), c.getCompanyNIT(), c.getEmail(), c.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public CompanyDTO getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id " + id));
        return new CompanyDTO(company.getId(), company.getCompanyName(), company.getCompanyNIT(), company.getEmail(), company.getCreatedAt());
    }

    public CompanyDTO createCompany(Company company) {
        if(companyRepository.existsByEmail(company.getEmail()))
            throw new IllegalArgumentException("Email already exists");
        if(companyRepository.existsByCompanyNIT(company.getCompanyNIT()))
            throw new IllegalArgumentException("Company NIT already exists");
        Company saved = companyRepository.save(company);
        return new CompanyDTO(saved.getId(), saved.getCompanyName(), saved.getCompanyNIT(), saved.getEmail(), saved.getCreatedAt());
    }

    public CompanyDTO updateCompanyPartial(Long id, Company companyUpdates) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id " + id));

        if(companyUpdates.getCompanyName() != null) company.setCompanyName(companyUpdates.getCompanyName());
        if(companyUpdates.getEmail() != null) company.setEmail(companyUpdates.getEmail());
        if(companyUpdates.getCompanyNIT() != null) company.setCompanyNIT(companyUpdates.getCompanyNIT());

        Company updated = companyRepository.save(company);
        return new CompanyDTO(updated.getId(), updated.getCompanyName(), updated.getCompanyNIT(), updated.getEmail(), updated.getCreatedAt());
    }

    public void deleteCompany(Long id) {
        if(!companyRepository.existsById(id))
            throw new ResourceNotFoundException("Company not found with id " + id);
        companyRepository.deleteById(id);
    }

    public Optional<Object> findById(Long companyId) {
        return companyRepository.findById(companyId).map(c -> new CompanyDTO(c.getId(), c.getCompanyName(), c.getCompanyNIT(), c.getEmail(), c.getCreatedAt()));
    }
}
