package com.company.management.company_crud.service.impl;

import com.company.management.company_crud.mapper.TeamMapper;
import com.company.management.company_crud.model.dao.Department;
import com.company.management.company_crud.model.dao.Team;
import com.company.management.company_crud.model.dto.TeamDTO;
import com.company.management.company_crud.repository.DepartmentRepository;
import com.company.management.company_crud.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeamServiceImplTest {

    @InjectMocks
    private TeamServiceImpl teamService;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private TeamMapper teamMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTeamsByDepartmentId() {
        // Arrange
        Long departmentId = 1L;

        Department department = new Department();
        department.setId(departmentId);

        Team team1 = new Team();
        team1.setId(1L);
        team1.setName("Team A");
        team1.setDepartment(department);

        Team team2 = new Team();
        team2.setId(2L);
        team2.setName("Team B");
        team2.setDepartment(department);

        List<Team> teams = List.of(team1, team2);

        TeamDTO teamDTO1 = new TeamDTO();
        teamDTO1.setId(1L);
        teamDTO1.setName("Team A");

        TeamDTO teamDTO2 = new TeamDTO();
        teamDTO2.setId(2L);
        teamDTO2.setName("Team B");

        when(teamRepository.findAll()).thenReturn(teams);
        when(teamMapper.teamToTeamDTO(team1)).thenReturn(teamDTO1);
        when(teamMapper.teamToTeamDTO(team2)).thenReturn(teamDTO2);

        // Act
        List<TeamDTO> result = teamService.getTeamsByDepartmentId(departmentId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Team A", result.get(0).getName());
        assertEquals("Team B", result.get(1).getName());

        verify(teamRepository, times(1)).findAll();
        verify(teamMapper, times(1)).teamToTeamDTO(team1);
        verify(teamMapper, times(1)).teamToTeamDTO(team2);
    }

    @Test
    void testGetTeamById() {
        // Arrange
        Long teamId = 1L;

        Team team = new Team();
        team.setId(teamId);
        team.setName("Team A");

        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setId(teamId);
        teamDTO.setName("Team A");

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(teamMapper.teamToTeamDTO(team)).thenReturn(teamDTO);

        // Act
        TeamDTO result = teamService.getTeamById(teamId);

        // Assert
        assertNotNull(result);
        assertEquals(teamId, result.getId());
        assertEquals("Team A", result.getName());

        verify(teamRepository, times(1)).findById(teamId);
        verify(teamMapper, times(1)).teamToTeamDTO(team);
    }

    @Test
    void testGetTeamById_NotFound() {
        // Arrange
        Long teamId = 1L;

        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            teamService.getTeamById(teamId);
        });

        assertEquals("Team not found", exception.getMessage());
        verify(teamRepository, times(1)).findById(teamId);
        verifyNoInteractions(teamMapper);
    }

    @Test
    void testCreateTeam() {
        // Arrange
        Long departmentId = 1L;

        Department department = new Department();
        department.setId(departmentId);

        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setName("New Team");

        Team team = new Team();
        team.setName("New Team");

        Team savedTeam = new Team();
        savedTeam.setId(1L);
        savedTeam.setName("New Team");
        savedTeam.setDepartment(department);

        TeamDTO savedTeamDTO = new TeamDTO();
        savedTeamDTO.setId(1L);
        savedTeamDTO.setName("New Team");

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        when(teamMapper.teamDTOToTeam(teamDTO)).thenReturn(team);
        when(teamRepository.save(team)).thenReturn(savedTeam);
        when(teamMapper.teamToTeamDTO(savedTeam)).thenReturn(savedTeamDTO);

        // Act
        TeamDTO result = teamService.createTeam(departmentId, teamDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Team", result.getName());

        verify(departmentRepository, times(1)).findById(departmentId);
        verify(teamMapper, times(1)).teamDTOToTeam(teamDTO);
        verify(teamRepository, times(1)).save(team);
        verify(teamMapper, times(1)).teamToTeamDTO(savedTeam);
    }

    @Test
    void testCreateTeam_DepartmentNotFound() {
        // Arrange
        Long departmentId = 1L;
        TeamDTO teamDTO = new TeamDTO();

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            teamService.createTeam(departmentId, teamDTO);
        });

        assertEquals("Department not found", exception.getMessage());
        verify(departmentRepository, times(1)).findById(departmentId);
        verifyNoInteractions(teamMapper);
        verifyNoInteractions(teamRepository);
    }


    @Test
    void testUpdateTeam() {
        // Arrange
        Long teamId = 1L;

        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setName("Updated Team");

        Team existingTeam = new Team();
        existingTeam.setId(teamId);
        existingTeam.setName("Old Team");

        Team updatedTeam = new Team();
        updatedTeam.setId(teamId);
        updatedTeam.setName("Updated Team");

        TeamDTO updatedTeamDTO = new TeamDTO();
        updatedTeamDTO.setId(teamId);
        updatedTeamDTO.setName("Updated Team");

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(existingTeam));
        when(teamRepository.save(existingTeam)).thenReturn(updatedTeam);
        when(teamMapper.teamToTeamDTO(updatedTeam)).thenReturn(updatedTeamDTO);

        // Act
        TeamDTO result = teamService.updateTeam(teamId, teamDTO);

        // Assert
        assertNotNull(result);
        assertEquals(teamId, result.getId());
        assertEquals("Updated Team", result.getName());

        verify(teamRepository, times(1)).findById(teamId);
        verify(teamRepository, times(1)).save(existingTeam);
        verify(teamMapper, times(1)).teamToTeamDTO(updatedTeam);
    }

    @Test
    void testDeleteTeam() {
        // Arrange
        Long teamId = 1L;

        // Act
        teamService.deleteTeam(teamId);

        // Assert
        verify(teamRepository, times(1)).deleteById(teamId);
    }

}
