package com.company.management.company_crud.service;

import com.company.management.company_crud.model.dto.CompanyDTO;

import java.util.List;

public interface CompanyService {
    List<CompanyDTO> getAllCompanies();

    CompanyDTO getCompanyById(Long id);

    CompanyDTO createCompany(CompanyDTO companyDTO);

    CompanyDTO updateCompany(Long id, CompanyDTO companyDTO);

    void deleteCompany(Long id);
}
