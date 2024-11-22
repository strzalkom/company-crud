package com.company.management.company_crud.controller;

import com.company.management.company_crud.model.dto.DepartmentDTO;
import com.company.management.company_crud.service.DepartmentService;
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

@WebMvcTest(controllers = DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        Mockito.reset(departmentService);
    }

    @Test
    void testGetDepartmentsByCompanyId() throws Exception {
        // Arrange
        Long companyId = 1L;

        DepartmentDTO department1 = new DepartmentDTO();
        department1.setId(1L);
        department1.setName("Department A");

        DepartmentDTO department2 = new DepartmentDTO();
        department2.setId(2L);
        department2.setName("Department B");

        List<DepartmentDTO> departments = Arrays.asList(department1, department2);

        when(departmentService.getDepartmentsByCompanyId(companyId)).thenReturn(departments);

        // Act & Assert
        mockMvc.perform(get("/api/departments/company/{companyId}", companyId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Department A"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Department B"));

        verify(departmentService, times(1)).getDepartmentsByCompanyId(companyId);
    }
    @Test
    void testGetDepartmentById() throws Exception {
        // Arrange
        Long departmentId = 1L;

        DepartmentDTO department = new DepartmentDTO();
        department.setId(departmentId);
        department.setName("Department A");

        when(departmentService.getDepartmentById(departmentId)).thenReturn(department);

        // Act & Assert
        mockMvc.perform(get("/api/departments/{id}", departmentId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(departmentId))
                .andExpect(jsonPath("$.name").value("Department A"));

        verify(departmentService, times(1)).getDepartmentById(departmentId);
    }
    @Test
    void testCreateDepartment() throws Exception {
        // Arrange
        Long companyId = 1L;

        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setName("New Department");

        DepartmentDTO savedDepartment = new DepartmentDTO();
        savedDepartment.setId(1L);
        savedDepartment.setName("New Department");

        when(departmentService.createDepartment(eq(companyId), any(DepartmentDTO.class))).thenReturn(savedDepartment);

        // Act & Assert
        mockMvc.perform(post("/api/departments/company/{companyId}", companyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Department\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("New Department"));

        verify(departmentService, times(1)).createDepartment(eq(companyId), any(DepartmentDTO.class));
    }
    @Test
    void testUpdateDepartment() throws Exception {
        // Arrange
        Long departmentId = 1L;

        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setName("Updated Department");

        DepartmentDTO updatedDepartment = new DepartmentDTO();
        updatedDepartment.setId(departmentId);
        updatedDepartment.setName("Updated Department");

        when(departmentService.updateDepartment(eq(departmentId), any(DepartmentDTO.class))).thenReturn(updatedDepartment);

        // Act & Assert
        mockMvc.perform(put("/api/departments/{id}", departmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Department\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(departmentId))
                .andExpect(jsonPath("$.name").value("Updated Department"));

        verify(departmentService, times(1)).updateDepartment(eq(departmentId), any(DepartmentDTO.class));
    }
    @Test
    void testDeleteDepartment() throws Exception {
        // Arrange
        Long departmentId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/departments/{id}", departmentId))
                .andExpect(status().isNoContent());

        verify(departmentService, times(1)).deleteDepartment(departmentId);
    }

}
