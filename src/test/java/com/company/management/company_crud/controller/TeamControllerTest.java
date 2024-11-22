package com.company.management.company_crud.controller;

import com.company.management.company_crud.model.dto.TeamDTO;
import com.company.management.company_crud.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TeamController.class)
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamService teamService;

    @BeforeEach
    void setUp() {
        Mockito.reset(teamService);
    }

    @Test
    void testGetTeamsByDepartmentId() throws Exception {
        // Arrange
        Long departmentId = 1L;

        TeamDTO team1 = new TeamDTO();
        team1.setId(1L);
        team1.setName("Team A");

        TeamDTO team2 = new TeamDTO();
        team2.setId(2L);
        team2.setName("Team B");

        List<TeamDTO> teams = Arrays.asList(team1, team2);

        when(teamService.getTeamsByDepartmentId(departmentId)).thenReturn(teams);

        // Act & Assert
        mockMvc.perform(get("/api/teams/department/{departmentId}", departmentId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Team A"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Team B"));

        verify(teamService, times(1)).getTeamsByDepartmentId(departmentId);
    }
    @Test
    void testGetTeamById() throws Exception {
        // Arrange
        Long teamId = 1L;

        TeamDTO team = new TeamDTO();
        team.setId(teamId);
        team.setName("Team A");

        when(teamService.getTeamById(teamId)).thenReturn(team);

        // Act & Assert
        mockMvc.perform(get("/api/teams/{id}", teamId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(teamId))
                .andExpect(jsonPath("$.name").value("Team A"));

        verify(teamService, times(1)).getTeamById(teamId);
    }
    @Test
    void testCreateTeam() throws Exception {
        // Arrange
        Long departmentId = 1L;

        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setName("New Team");

        TeamDTO savedTeam = new TeamDTO();
        savedTeam.setId(1L);
        savedTeam.setName("New Team");

        when(teamService.createTeam(eq(departmentId), any(TeamDTO.class))).thenReturn(savedTeam);

        // Act & Assert
        mockMvc.perform(post("/api/teams/department/{departmentId}", departmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Team\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("New Team"));

        verify(teamService, times(1)).createTeam(eq(departmentId), any(TeamDTO.class));
    }
    @Test
    void testUpdateTeam() throws Exception {
        // Arrange
        Long teamId = 1L;

        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setName("Updated Team");

        TeamDTO updatedTeam = new TeamDTO();
        updatedTeam.setId(teamId);
        updatedTeam.setName("Updated Team");

        when(teamService.updateTeam(eq(teamId), any(TeamDTO.class))).thenReturn(updatedTeam);

        // Act & Assert
        mockMvc.perform(put("/api/teams/{id}", teamId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Team\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(teamId))
                .andExpect(jsonPath("$.name").value("Updated Team"));

        verify(teamService, times(1)).updateTeam(eq(teamId), any(TeamDTO.class));
    }
    @Test
    void testDeleteTeam() throws Exception {
        // Arrange
        Long teamId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/teams/{id}", teamId))
                .andExpect(status().isNoContent());

        verify(teamService, times(1)).deleteTeam(teamId);
    }

}
