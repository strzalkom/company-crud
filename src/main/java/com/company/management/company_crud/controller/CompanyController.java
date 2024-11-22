package com.company.management.company_crud.controller;

import com.company.management.company_crud.model.dto.CompanyDTO;
import com.company.management.company_crud.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@Tag(name = "Company Management", description = "Endpoints for managing companies")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    @Operation(
            summary = "Get all companies",
            description = "Fetches a list of all companies stored in the system."
    )
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a company by ID",
            description = "Fetches a specific company by its unique ID."
    )
    public ResponseEntity<CompanyDTO> getCompanyById(
            @Parameter(description = "ID of the company to fetch", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @PostMapping
    @Operation(
            summary = "Create a new company",
            description = "Creates a new company in the system."
    )
    public ResponseEntity<CompanyDTO> createCompany(
            @RequestBody(description = "Details of the company to create", required = true)
            @org.springframework.web.bind.annotation.RequestBody CompanyDTO companyDTO) {
        return new ResponseEntity<>(companyService.createCompany(companyDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a company",
            description = "Updates the details of an existing company by its ID."
    )
    public ResponseEntity<CompanyDTO> updateCompany(
            @Parameter(description = "ID of the company to update", example = "1")
            @PathVariable Long id,
            @RequestBody(description = "Updated details of the company", required = true)
            @org.springframework.web.bind.annotation.RequestBody CompanyDTO companyDTO) {
        return ResponseEntity.ok(companyService.updateCompany(id, companyDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a company",
            description = "Deletes a company by its ID."
    )
    public ResponseEntity<Void> deleteCompany(
            @Parameter(description = "ID of the company to delete", example = "1")
            @PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}
