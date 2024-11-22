package com.company.management.company_crud.controller;

import com.company.management.company_crud.model.dto.CompanyDTO;
import com.company.management.company_crud.service.CompanyService;
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

@WebMvcTest(controllers = CompanyController.class)
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        Mockito.reset(companyService);
    }

    @Test
    void testGetAllCompanies() throws Exception {
        // Arrange
        CompanyDTO company1 = new CompanyDTO();
        company1.setId(1L);
        company1.setName("Company A");

        CompanyDTO company2 = new CompanyDTO();
        company2.setId(2L);
        company2.setName("Company B");

        List<CompanyDTO> companies = Arrays.asList(company1, company2);

        when(companyService.getAllCompanies()).thenReturn(companies);

        // Act & Assert
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
    void testGetCompanyById() throws Exception {
        // Arrange
        Long companyId = 1L;
        CompanyDTO company = new CompanyDTO();
        company.setId(companyId);
        company.setName("Company A");

        when(companyService.getCompanyById(companyId)).thenReturn(company);

        // Act & Assert
        mockMvc.perform(get("/api/companies/{id}", companyId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(companyId))
                .andExpect(jsonPath("$.name").value("Company A"));

        verify(companyService, times(1)).getCompanyById(companyId);
    }
    @Test
    void testCreateCompany() throws Exception {
        // Arrange
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setName("New Company");

        CompanyDTO savedCompany = new CompanyDTO();
        savedCompany.setId(1L);
        savedCompany.setName("New Company");

        when(companyService.createCompany(any(CompanyDTO.class))).thenReturn(savedCompany);

        // Act & Assert
        mockMvc.perform(post("/api/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Company\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("New Company"));

        verify(companyService, times(1)).createCompany(any(CompanyDTO.class));
    }
    @Test
    void testUpdateCompany() throws Exception {
        // Arrange
        Long companyId = 1L;

        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setName("Updated Company");

        CompanyDTO updatedCompany = new CompanyDTO();
        updatedCompany.setId(companyId);
        updatedCompany.setName("Updated Company");

        when(companyService.updateCompany(eq(companyId), any(CompanyDTO.class))).thenReturn(updatedCompany);

        // Act & Assert
        mockMvc.perform(put("/api/companies/{id}", companyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Company\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(companyId))
                .andExpect(jsonPath("$.name").value("Updated Company"));

        verify(companyService, times(1)).updateCompany(eq(companyId), any(CompanyDTO.class));
    }
    @Test
    void testDeleteCompany() throws Exception {
        // Arrange
        Long companyId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/companies/{id}", companyId))
                .andExpect(status().isNoContent());

        verify(companyService, times(1)).deleteCompany(companyId);
    }

}
