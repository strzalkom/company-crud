package com.company.management.company_crud.controller;

import com.company.management.company_crud.model.dto.DepartmentDTO;
import com.company.management.company_crud.service.DepartmentService;
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
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@Tag(name = "Department Management", description = "Endpoints for managing departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/company/{companyId}")
    @Operation(
            summary = "Get departments by company ID",
            description = "Fetches all departments associated with a specific company."
    )
    public ResponseEntity<List<DepartmentDTO>> getDepartmentsByCompanyId(
            @Parameter(description = "ID of the company to fetch departments for", example = "1")
            @PathVariable Long companyId) {
        return ResponseEntity.ok(departmentService.getDepartmentsByCompanyId(companyId));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a department by ID",
            description = "Fetches details of a specific department by its ID."
    )
    public ResponseEntity<DepartmentDTO> getDepartmentById(
            @Parameter(description = "ID of the department to fetch", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @PostMapping("/company/{companyId}")
    @Operation(
            summary = "Create a new department",
            description = "Creates a new department under a specific company."
    )
    public ResponseEntity<DepartmentDTO> createDepartment(
            @Parameter(description = "ID of the company to create the department under", example = "1")
            @PathVariable Long companyId,
            @RequestBody(description = "Details of the department to create", required = true)
            @org.springframework.web.bind.annotation.RequestBody DepartmentDTO departmentDTO) {
        return new ResponseEntity<>(departmentService.createDepartment(companyId, departmentDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a department",
            description = "Updates the details of an existing department by its ID."
    )
    public ResponseEntity<DepartmentDTO> updateDepartment(
            @Parameter(description = "ID of the department to update", example = "1")
            @PathVariable Long id,
            @RequestBody(description = "Updated details of the department", required = true)
            @org.springframework.web.bind.annotation.RequestBody DepartmentDTO departmentDTO) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, departmentDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a department",
            description = "Deletes a department by its ID."
    )
    public ResponseEntity<Void> deleteDepartment(
            @Parameter(description = "ID of the department to delete", example = "1")
            @PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
