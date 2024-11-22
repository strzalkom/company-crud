package com.company.management.company_crud.controller;

import com.company.management.company_crud.model.dto.ManagerDTO;
import com.company.management.company_crud.service.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/managers")
@RequiredArgsConstructor
@Tag(name = "Manager Management", description = "Endpoints for managing managers")
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a manager by ID",
            description = "Fetches details of a specific manager by their unique ID."
    )
    public ResponseEntity<ManagerDTO> getManagerById(
            @Parameter(description = "ID of the manager to fetch", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(managerService.getManagerById(id));
    }

    @PostMapping
    @Operation(
            summary = "Create a new manager",
            description = "Creates a new manager in the system."
    )
    public ResponseEntity<ManagerDTO> createManager(
            @RequestBody(description = "Details of the manager to create", required = true)
            @org.springframework.web.bind.annotation.RequestBody ManagerDTO managerDTO) {
        return new ResponseEntity<>(managerService.createManager(managerDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a manager",
            description = "Updates the details of an existing manager by their ID."
    )
    public ResponseEntity<ManagerDTO> updateManager(
            @Parameter(description = "ID of the manager to update", example = "1")
            @PathVariable Long id,
            @RequestBody(description = "Updated details of the manager", required = true)
            @org.springframework.web.bind.annotation.RequestBody ManagerDTO managerDTO) {
        return ResponseEntity.ok(managerService.updateManager(id, managerDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a manager",
            description = "Deletes a manager by their ID."
    )
    public ResponseEntity<Void> deleteManager(
            @Parameter(description = "ID of the manager to delete", example = "1")
            @PathVariable Long id) {
        managerService.deleteManager(id);
        return ResponseEntity.noContent().build();
    }
}
