package com.company.management.company_crud.controller;

import com.company.management.company_crud.model.dto.ProjectDTO;
import com.company.management.company_crud.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "Project Management", description = "Endpoints for managing projects")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a project by ID",
            description = "Fetches details of a specific project by its unique ID."
    )
    public ResponseEntity<ProjectDTO> getProjectById(
            @Parameter(description = "ID of the project to fetch", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PostMapping("/team/{teamId}")
    @Operation(
            summary = "Create a new project",
            description = "Creates a new project under a specific team."
    )
    public ResponseEntity<ProjectDTO> createProject(
            @Parameter(description = "ID of the team to create the project under", example = "1")
            @PathVariable Long teamId,
            @RequestBody(description = "Details of the project to create", required = true)
            @org.springframework.web.bind.annotation.RequestBody ProjectDTO projectDTO) {
        return new ResponseEntity<>(projectService.createProject(teamId, projectDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a project",
            description = "Updates the details of an existing project by its ID."
    )
    public ResponseEntity<ProjectDTO> updateProject(
            @Parameter(description = "ID of the project to update", example = "1")
            @PathVariable Long id,
            @RequestBody(description = "Updated details of the project", required = true)
            @org.springframework.web.bind.annotation.RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.ok(projectService.updateProject(id, projectDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a project",
            description = "Deletes a project by its ID."
    )
    public ResponseEntity<Void> deleteProject(
            @Parameter(description = "ID of the project to delete", example = "1")
            @PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
