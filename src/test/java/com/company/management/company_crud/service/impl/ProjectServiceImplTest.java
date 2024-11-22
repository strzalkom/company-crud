package com.company.management.company_crud.service.impl;

import com.company.management.company_crud.mapper.ProjectMapper;
import com.company.management.company_crud.model.dao.Project;
import com.company.management.company_crud.model.dao.Team;
import com.company.management.company_crud.model.dto.ProjectDTO;
import com.company.management.company_crud.repository.ProjectRepository;
import com.company.management.company_crud.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceImplTest {

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private ProjectMapper projectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProjectById() {
        // Arrange
        Long projectId = 1L;

        Project project = new Project();
        project.setId(projectId);
        project.setName("Project A");

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(projectId);
        projectDTO.setName("Project A");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectMapper.projectToProjectDTO(project)).thenReturn(projectDTO);

        // Act
        ProjectDTO result = projectService.getProjectById(projectId);

        // Assert
        assertNotNull(result);
        assertEquals(projectId, result.getId());
        assertEquals("Project A", result.getName());

        verify(projectRepository, times(1)).findById(projectId);
        verify(projectMapper, times(1)).projectToProjectDTO(project);
    }
    @Test
    void testGetProjectById_NotFound() {
        // Arrange
        Long projectId = 1L;

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            projectService.getProjectById(projectId);
        });

        assertEquals("Project not found", exception.getMessage());
        verify(projectRepository, times(1)).findById(projectId);
        verifyNoInteractions(projectMapper);
    }
    @Test
    void testCreateProject() {
        // Arrange
        Long teamId = 1L;

        Team team = new Team();
        team.setId(teamId);

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("New Project");

        Project project = new Project();
        project.setName("New Project");

        Project savedProject = new Project();
        savedProject.setId(1L);
        savedProject.setName("New Project");
        savedProject.setTeam(team);

        ProjectDTO savedProjectDTO = new ProjectDTO();
        savedProjectDTO.setId(1L);
        savedProjectDTO.setName("New Project");

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(projectMapper.projectDTOToProject(projectDTO)).thenReturn(project);
        when(projectRepository.save(project)).thenReturn(savedProject);
        when(projectMapper.projectToProjectDTO(savedProject)).thenReturn(savedProjectDTO);

        // Act
        ProjectDTO result = projectService.createProject(teamId, projectDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Project", result.getName());

        verify(teamRepository, times(1)).findById(teamId);
        verify(projectMapper, times(1)).projectDTOToProject(projectDTO);
        verify(projectRepository, times(1)).save(project);
        verify(projectMapper, times(1)).projectToProjectDTO(savedProject);
    }
    @Test
    void testCreateProject_TeamNotFound() {
        // Arrange
        Long teamId = 1L;
        ProjectDTO projectDTO = new ProjectDTO();

        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            projectService.createProject(teamId, projectDTO);
        });

        assertEquals("Team not found", exception.getMessage());
        verify(teamRepository, times(1)).findById(teamId);
        verifyNoInteractions(projectMapper);
        verifyNoInteractions(projectRepository);
    }
    @Test
    void testUpdateProject() {
        // Arrange
        Long projectId = 1L;

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("Updated Project");

        Project existingProject = new Project();
        existingProject.setId(projectId);
        existingProject.setName("Old Project");

        Project updatedProject = new Project();
        updatedProject.setId(projectId);
        updatedProject.setName("Updated Project");

        ProjectDTO updatedProjectDTO = new ProjectDTO();
        updatedProjectDTO.setId(projectId);
        updatedProjectDTO.setName("Updated Project");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProject));
        when(projectRepository.save(existingProject)).thenReturn(updatedProject);
        when(projectMapper.projectToProjectDTO(updatedProject)).thenReturn(updatedProjectDTO);

        // Act
        ProjectDTO result = projectService.updateProject(projectId, projectDTO);

        // Assert
        assertNotNull(result);
        assertEquals(projectId, result.getId());
        assertEquals("Updated Project", result.getName());

        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, times(1)).save(existingProject);
        verify(projectMapper, times(1)).projectToProjectDTO(updatedProject);
    }
    @Test
    void testDeleteProject() {
        // Arrange
        Long projectId = 1L;

        // Act
        projectService.deleteProject(projectId);

        // Assert
        verify(projectRepository, times(1)).deleteById(projectId);
    }

}
