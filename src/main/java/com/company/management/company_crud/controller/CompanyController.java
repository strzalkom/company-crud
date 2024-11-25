package com.company.management.company_crud.controller;

import com.company.management.company_crud.model.dto.*;
import com.company.management.company_crud.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Company Management", description = "Endpoints for managing companies, departments, teams, projects, and managers")
public class CompanyController {

    private final CompanyService companyService;
    private final DepartmentService departmentService;
    private final TeamService teamService;
    private final ProjectService projectService;
    private final ManagerService managerService;

    // ===================== Company Endpoints =====================
    @GetMapping("/companies")
    @Operation(summary = "Get all companies", description = "Fetches a list of all companies.")
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/companies/{id}")
    @Operation(summary = "Get a company by ID", description = "Fetches details of a company by its ID.")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @PostMapping("/companies")
    @Operation(summary = "Create a new company", description = "Creates a new company.")
    public ResponseEntity<CompanyDTO> createCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        return new ResponseEntity<>(companyService.createCompany(companyDTO), HttpStatus.CREATED);
    }

    @PutMapping("/companies/{id}")
    @Operation(summary = "Update a company", description = "Updates details of a specific company.")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable Long id, @RequestBody CompanyDTO companyDTO) {
        return ResponseEntity.ok(companyService.updateCompany(id, companyDTO));
    }

    @DeleteMapping("/companies/{id}")
    @Operation(summary = "Delete a company", description = "Deletes a company by its ID.")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }

    // ===================== Department Endpoints =====================
    @GetMapping("/companies/{companyId}/departments")
    @Operation(summary = "Get all departments of a company", description = "Fetches all departments under a specific company.")
    public ResponseEntity<List<DepartmentDTO>> getDepartmentsByCompanyId(@PathVariable Long companyId) {
        return ResponseEntity.ok(departmentService.getDepartmentsByCompanyId(companyId));
    }

    @GetMapping("/departments/{id}")
    @Operation(summary = "Get a department by ID", description = "Fetches details of a department by its ID.")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @PostMapping("/companies/{companyId}/departments")
    @Operation(summary = "Create a department", description = "Creates a new department under a specific company.")
    public ResponseEntity<DepartmentDTO> createDepartment(@PathVariable Long companyId, @Valid @RequestBody DepartmentDTO departmentDTO) {
        return new ResponseEntity<>(departmentService.createDepartment(companyId, departmentDTO), HttpStatus.CREATED);
    }

    @PutMapping("/departments/{id}")
    @Operation(summary = "Update a department", description = "Updates a department's details.")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Long id, @RequestBody DepartmentDTO departmentDTO) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, departmentDTO));
    }

    @DeleteMapping("/departments/{id}")
    @Operation(summary = "Delete a department", description = "Deletes a department by its ID.")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    // ===================== Team Endpoints =====================
    @GetMapping("/departments/{departmentId}/teams")
    @Operation(summary = "Get all teams of a department", description = "Fetches all teams under a specific department.")
    public ResponseEntity<List<TeamDTO>> getTeamsByDepartmentId(@PathVariable Long departmentId) {
        return ResponseEntity.ok(teamService.getTeamsByDepartmentId(departmentId));
    }

    @GetMapping("/teams/{id}")
    @Operation(summary = "Get a team by ID", description = "Fetches details of a team by its ID.")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @PostMapping("/departments/{departmentId}/teams")
    @Operation(summary = "Create a team", description = "Creates a new team under a specific department.")
    public ResponseEntity<TeamDTO> createTeam(@PathVariable Long departmentId, @Valid @RequestBody TeamDTO teamDTO) {
        return new ResponseEntity<>(teamService.createTeam(departmentId, teamDTO), HttpStatus.CREATED);
    }

    @PutMapping("/teams/{id}")
    @Operation(summary = "Update a team", description = "Updates a team's details.")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable Long id, @RequestBody TeamDTO teamDTO) {
        return ResponseEntity.ok(teamService.updateTeam(id, teamDTO));
    }

    @DeleteMapping("/teams/{id}")
    @Operation(summary = "Delete a team", description = "Deletes a team by its ID.")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

    // ===================== Project Endpoints =====================
    @GetMapping("/projects/{id}")
    @Operation(summary = "Get a project by ID", description = "Fetches details of a project by its ID.")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PostMapping("/teams/{teamId}/projects")
    @Operation(summary = "Create a project", description = "Creates a new project under a specific team.")
    public ResponseEntity<ProjectDTO> createProject(@PathVariable Long teamId, @Valid @RequestBody ProjectDTO projectDTO) {
        return new ResponseEntity<>(projectService.createProject(teamId, projectDTO), HttpStatus.CREATED);
    }

    @PutMapping("/projects/{id}")
    @Operation(summary = "Update a project", description = "Updates a project's details.")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long id, @RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.ok(projectService.updateProject(id, projectDTO));
    }

    @DeleteMapping("/projects/{id}")
    @Operation(summary = "Delete a project", description = "Deletes a project by its ID.")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    // ===================== Manager Endpoints =====================
    @GetMapping("/managers/{id}")
    @Operation(summary = "Get a manager by ID", description = "Fetches details of a manager by their ID.")
    public ResponseEntity<ManagerDTO> getManagerById(@PathVariable Long id) {
        return ResponseEntity.ok(managerService.getManagerById(id));
    }

    @PostMapping("/managers")
    @Operation(summary = "Create a manager", description = "Creates a new manager.")
    public ResponseEntity<ManagerDTO> createManager(@Valid @RequestBody ManagerDTO managerDTO) {
        return new ResponseEntity<>(managerService.createManager(managerDTO), HttpStatus.CREATED);
    }

    @PutMapping("/managers/{id}")
    @Operation(summary = "Update a manager", description = "Updates a manager's details.")
    public ResponseEntity<ManagerDTO> updateManager(@PathVariable Long id, @RequestBody ManagerDTO managerDTO) {
        return ResponseEntity.ok(managerService.updateManager(id, managerDTO));
    }

    @DeleteMapping("/managers/{id}")
    @Operation(summary = "Delete a manager", description = "Deletes a manager by their ID.")
    public ResponseEntity<Void> deleteManager(@PathVariable Long id) {
        managerService.deleteManager(id);
        return ResponseEntity.noContent().build();
    }


}
