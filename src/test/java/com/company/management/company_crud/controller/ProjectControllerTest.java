package com.company.management.company_crud.controller;

import com.company.management.company_crud.model.dto.ProjectDTO;
import com.company.management.company_crud.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        Mockito.reset(projectService);
    }

    @Test
    void testGetProjectById() throws Exception {
        // Arrange
        Long projectId = 1L;

        ProjectDTO project = new ProjectDTO();
        project.setId(projectId);
        project.setName("Project A");

        when(projectService.getProjectById(projectId)).thenReturn(project);

        // Act & Assert
        mockMvc.perform(get("/api/projects/{id}", projectId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(projectId))
                .andExpect(jsonPath("$.name").value("Project A"));

        verify(projectService, times(1)).getProjectById(projectId);
    }

    @Test
    void testCreateProject() throws Exception {
        // Arrange
        Long teamId = 1L;

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("New Project");

        ProjectDTO savedProject = new ProjectDTO();
        savedProject.setId(1L);
        savedProject.setName("New Project");

        when(projectService.createProject(eq(teamId), any(ProjectDTO.class))).thenReturn(savedProject);

        // Act & Assert
        mockMvc.perform(post("/api/projects/team/{teamId}", teamId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Project\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("New Project"));

        verify(projectService, times(1)).createProject(eq(teamId), any(ProjectDTO.class));
    }
    @Test
    void testUpdateProject() throws Exception {
        // Arrange
        Long projectId = 1L;

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("Updated Project");

        ProjectDTO updatedProject = new ProjectDTO();
        updatedProject.setId(projectId);
        updatedProject.setName("Updated Project");

        when(projectService.updateProject(eq(projectId), any(ProjectDTO.class))).thenReturn(updatedProject);

        // Act & Assert
        mockMvc.perform(put("/api/projects/{id}", projectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Project\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(projectId))
                .andExpect(jsonPath("$.name").value("Updated Project"));

        verify(projectService, times(1)).updateProject(eq(projectId), any(ProjectDTO.class));
    }
    @Test
    void testDeleteProject() throws Exception {
        // Arrange
        Long projectId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/projects/{id}", projectId))
                .andExpect(status().isNoContent());

        verify(projectService, times(1)).deleteProject(projectId);
    }

}
