package com.company.management.company_crud.controller;

import com.company.management.company_crud.model.dto.*;
import com.company.management.company_crud.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CompanyController.class)
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @MockBean
    private DepartmentService departmentService;

    @MockBean
    private TeamService teamService;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private ManagerService managerService;

    @BeforeEach
    void setUp() {
        Mockito.reset(companyService, departmentService, teamService, projectService, managerService);
    }

    // ===================== Company Endpoints =====================
    @Test
    void testGetAllCompanies_Positive() throws Exception {
        CompanyDTO company1 = new CompanyDTO();
        company1.setId(1L);
        company1.setName("Company A");

        CompanyDTO company2 = new CompanyDTO();
        company2.setId(2L);
        company2.setName("Company B");

        when(companyService.getAllCompanies()).thenReturn(Arrays.asList(company1, company2));

        mockMvc.perform(get("/api/companies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Company A"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Company B"));

        verify(companyService, times(1)).getAllCompanies();
    }

    @Test
    void testGetCompanyById_Positive() throws Exception {
        CompanyDTO company = new CompanyDTO();
        company.setId(1L);
        company.setName("Company A");

        when(companyService.getCompanyById(1L)).thenReturn(company);

        mockMvc.perform(get("/api/companies/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Company A"));

        verify(companyService, times(1)).getCompanyById(1L);
    }

    @Test
    void testGetCompanyById_Negative_NotFound() throws Exception {
        when(companyService.getCompanyById(1L)).thenThrow(new RuntimeException("Company not found"));

        mockMvc.perform(get("/api/companies/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("ERROR"))
                .andExpect(jsonPath("$.message").value("Company not found"));
    }


    @Test
    void testCreateCompany_Positive() throws Exception {
        CompanyDTO inputCompany = new CompanyDTO();
        inputCompany.setName("New Company");

        CompanyDTO createdCompany = new CompanyDTO();
        createdCompany.setId(1L);
        createdCompany.setName("New Company");

        when(companyService.createCompany(any(CompanyDTO.class))).thenReturn(createdCompany);

        mockMvc.perform(post("/api/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Company\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("New Company"));

        verify(companyService, times(1)).createCompany(any());
    }

    @Test
    void testCreateCompany_Negative_BadRequest() throws Exception {
        mockMvc.perform(post("/api/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\"}")) // Puste dane
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").value("name: Name cannot be blank"));
    }


    @Test
    void testUpdateCompany_Positive() throws Exception {
        CompanyDTO updatedCompany = new CompanyDTO();
        updatedCompany.setId(1L);
        updatedCompany.setName("Updated Company");

        when(companyService.updateCompany(eq(1L), any(CompanyDTO.class))).thenReturn(updatedCompany);

        mockMvc.perform(put("/api/companies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Company\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated Company"));

        verify(companyService, times(1)).updateCompany(eq(1L), any());
    }

    @Test
    void testUpdateCompany_Negative_NotFound() throws Exception {
        when(companyService.updateCompany(eq(1L), any())).thenThrow(new RuntimeException("Company not found"));

        mockMvc.perform(put("/api/companies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Company\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Company not found"));

        verify(companyService, times(1)).updateCompany(eq(1L), any());
    }

    @Test
    void testDeleteCompany_Positive() throws Exception {
        doNothing().when(companyService).deleteCompany(1L);

        mockMvc.perform(delete("/api/companies/1"))
                .andExpect(status().isNoContent());

        verify(companyService, times(1)).deleteCompany(1L);
    }

    @Test
    void testDeleteCompany_Negative_NotFound() throws Exception {
        doThrow(new RuntimeException("Company not found")).when(companyService).deleteCompany(1L);

        mockMvc.perform(delete("/api/companies/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Company not found"));

        verify(companyService, times(1)).deleteCompany(1L);
    }

    // ===================== Department Endpoints =====================
    @Test
    void testGetDepartmentsByCompanyId_Positive() throws Exception {
        DepartmentDTO department1 = new DepartmentDTO();
        department1.setId(1L);
        department1.setName("Department A");

        DepartmentDTO department2 = new DepartmentDTO();
        department2.setId(2L);
        department2.setName("Department B");

        when(departmentService.getDepartmentsByCompanyId(1L)).thenReturn(Arrays.asList(department1, department2));

        mockMvc.perform(get("/api/companies/1/departments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Department A"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Department B"));

        verify(departmentService, times(1)).getDepartmentsByCompanyId(1L);
    }

    @Test
    void testGetDepartmentById_Positive() throws Exception {
        DepartmentDTO department = new DepartmentDTO();
        department.setId(1L);
        department.setName("Department A");

        when(departmentService.getDepartmentById(1L)).thenReturn(department);

        mockMvc.perform(get("/api/departments/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Department A"));

        verify(departmentService, times(1)).getDepartmentById(1L);
    }

    @Test
    void testGetDepartmentById_Negative_NotFound() throws Exception {
        when(departmentService.getDepartmentById(1L)).thenThrow(new RuntimeException("Department not found"));

        mockMvc.perform(get("/api/departments/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Department not found"));

        verify(departmentService, times(1)).getDepartmentById(1L);
    }


    @Test
    void testCreateDepartment_Positive() throws Exception {
        DepartmentDTO inputDepartment = new DepartmentDTO();
        inputDepartment.setName("New Department");

        DepartmentDTO createdDepartment = new DepartmentDTO();
        createdDepartment.setId(1L);
        createdDepartment.setName("New Department");

        when(departmentService.createDepartment(eq(1L), any())).thenReturn(createdDepartment);

        mockMvc.perform(post("/api/companies/1/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Department\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("New Department"));

        verify(departmentService, times(1)).createDepartment(eq(1L), any());
    }

    @Test
    void testCreateDepartment_Negative_BadRequest() throws Exception {
        mockMvc.perform(post("/api/companies/1/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\"}")) // Empty name
                .andExpect(status().isBadRequest());

        verify(departmentService, never()).createDepartment(eq(1L), any());
    }


    @Test
    void testUpdateDepartment_Positive() throws Exception {
        DepartmentDTO updatedDepartment = new DepartmentDTO();
        updatedDepartment.setId(1L);
        updatedDepartment.setName("Updated Department");

        when(departmentService.updateDepartment(eq(1L), any())).thenReturn(updatedDepartment);

        mockMvc.perform(put("/api/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Department\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated Department"));

        verify(departmentService, times(1)).updateDepartment(eq(1L), any());
    }

    @Test
    void testDeleteDepartment_Positive() throws Exception {
        doNothing().when(departmentService).deleteDepartment(1L);

        mockMvc.perform(delete("/api/departments/1"))
                .andExpect(status().isNoContent());

        verify(departmentService, times(1)).deleteDepartment(1L);
    }

    // ===================== Team Endpoints =====================
    @Test
    void testGetTeamsByDepartmentId_Positive() throws Exception {
        TeamDTO team1 = new TeamDTO();
        team1.setId(1L);
        team1.setName("Team A");

        TeamDTO team2 = new TeamDTO();
        team2.setId(2L);
        team2.setName("Team B");

        when(teamService.getTeamsByDepartmentId(1L)).thenReturn(Arrays.asList(team1, team2));

        mockMvc.perform(get("/api/departments/1/teams"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Team A"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Team B"));

        verify(teamService, times(1)).getTeamsByDepartmentId(1L);
    }

    @Test
    void testCreateTeam_Positive() throws Exception {
        TeamDTO inputTeam = new TeamDTO();
        inputTeam.setName("New Team");

        TeamDTO createdTeam = new TeamDTO();
        createdTeam.setId(1L);
        createdTeam.setName("New Team");

        when(teamService.createTeam(eq(1L), any())).thenReturn(createdTeam);

        mockMvc.perform(post("/api/departments/1/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Team\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("New Team"));

        verify(teamService, times(1)).createTeam(eq(1L), any());
    }

    // ===================== Project Endpoints =====================
    @Test
    void testGetProjectById_Positive() throws Exception {
        ProjectDTO project = new ProjectDTO();
        project.setId(1L);
        project.setName("Project A");

        when(projectService.getProjectById(1L)).thenReturn(project);

        mockMvc.perform(get("/api/projects/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Project A"));

        verify(projectService, times(1)).getProjectById(1L);
    }

    @Test
    void testGetProjectById_Negative_NotFound() throws Exception {
        when(projectService.getProjectById(1L)).thenThrow(new RuntimeException("Project not found"));

        mockMvc.perform(get("/api/projects/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Project not found"));

        verify(projectService, times(1)).getProjectById(1L);
    }


    @Test
    void testCreateProject_Positive() throws Exception {
        ProjectDTO inputProject = new ProjectDTO();
        inputProject.setName("New Project");

        ProjectDTO createdProject = new ProjectDTO();
        createdProject.setId(1L);
        createdProject.setName("New Project");

        when(projectService.createProject(eq(1L), any())).thenReturn(createdProject);

        mockMvc.perform(post("/api/teams/1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Project\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("New Project"));

        verify(projectService, times(1)).createProject(eq(1L), any());
    }

    @Test
    void testCreateProject_Negative_BadRequest() throws Exception {
        mockMvc.perform(post("/api/teams/1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\"}")) // Empty name
                .andExpect(status().isBadRequest());

        verify(projectService, never()).createProject(eq(1L), any());
    }


    // ===================== Manager Endpoints =====================
    @Test
    void testGetManagerById_Positive() throws Exception {
        ManagerDTO manager = new ManagerDTO();
        manager.setId(1L);
        manager.setName("Mateusz Strzałko");
        manager.setEmail("mateusz.strzalko@test.com");

        when(managerService.getManagerById(1L)).thenReturn(manager);

        mockMvc.perform(get("/api/managers/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Mateusz Strzałko"))
                .andExpect(jsonPath("$.email").value("mateusz.strzalko@test.com"));

        verify(managerService, times(1)).getManagerById(1L);
    }

    @Test
    void testDeleteManager_Negative_NotFound() throws Exception {

        doThrow(new RuntimeException("Manager not found")).when(managerService).deleteManager(1L);

        mockMvc.perform(delete("/api/managers/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("ERROR"))
                .andExpect(jsonPath("$.message").value("Manager not found"));

        verify(managerService, times(1)).deleteManager(1L);
    }


    @Test
    void testCreateManager_Positive() throws Exception {
        ManagerDTO inputManager = new ManagerDTO();
        inputManager.setName("Mateusz Strzałko");
        inputManager.setEmail("mateusz.strzalko@test.com");

        ManagerDTO createdManager = new ManagerDTO();
        createdManager.setId(1L);
        createdManager.setName("Mateusz Strzałko");
        createdManager.setEmail("mateusz.strzalko@test.com");

        when(managerService.createManager(any())).thenReturn(createdManager);

        mockMvc.perform(post("/api/managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Mateusz Strzałko\", \"email\": \"mateusz.strzalko@test.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Mateusz Strzałko"))
                .andExpect(jsonPath("$.email").value("mateusz.strzalko@test.com"));

        verify(managerService, times(1)).createManager(any());
    }

    @Test
    void testCreateManager_Negative_BadRequest() throws Exception {
        mockMvc.perform(post("/api/managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\", \"email\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").value("email: Email cannot be blank, name: Name cannot be blank"));
    }





}
