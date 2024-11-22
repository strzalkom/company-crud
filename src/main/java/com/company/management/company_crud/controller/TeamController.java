package com.company.management.company_crud.controller;

import com.company.management.company_crud.model.dto.TeamDTO;
import com.company.management.company_crud.service.TeamService;
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
@RequestMapping("/api/teams")
@RequiredArgsConstructor
@Tag(name = "Team Management", description = "Endpoints for managing teams")
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/department/{departmentId}")
    @Operation(
            summary = "Get teams by department ID",
            description = "Fetches all teams belonging to a specific department."
    )
    public ResponseEntity<List<TeamDTO>> getTeamsByDepartmentId(
            @Parameter(description = "ID of the department to fetch teams for", example = "1")
            @PathVariable Long departmentId) {
        return ResponseEntity.ok(teamService.getTeamsByDepartmentId(departmentId));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a team by ID",
            description = "Fetches details of a specific team by its ID."
    )
    public ResponseEntity<TeamDTO> getTeamById(
            @Parameter(description = "ID of the team to fetch", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @PostMapping("/department/{departmentId}")
    @Operation(
            summary = "Create a new team",
            description = "Creates a new team under a specific department."
    )
    public ResponseEntity<TeamDTO> createTeam(
            @Parameter(description = "ID of the department to create the team under", example = "1")
            @PathVariable Long departmentId,
            @RequestBody(description = "Details of the team to create", required = true)
            @org.springframework.web.bind.annotation.RequestBody TeamDTO teamDTO) {
        return new ResponseEntity<>(teamService.createTeam(departmentId, teamDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a team",
            description = "Updates the details of an existing team by its ID."
    )
    public ResponseEntity<TeamDTO> updateTeam(
            @Parameter(description = "ID of the team to update", example = "1")
            @PathVariable Long id,
            @RequestBody(description = "Updated details of the team", required = true)
            @org.springframework.web.bind.annotation.RequestBody TeamDTO teamDTO) {
        return ResponseEntity.ok(teamService.updateTeam(id, teamDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a team",
            description = "Deletes a team by its ID."
    )
    public ResponseEntity<Void> deleteTeam(
            @Parameter(description = "ID of the team to delete", example = "1")
            @PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}
