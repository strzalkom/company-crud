package com.company.management.company_crud.service.impl;

import com.company.management.company_crud.mapper.CompanyMapper;
import com.company.management.company_crud.model.dao.Company;
import com.company.management.company_crud.model.dto.CompanyDTO;
import com.company.management.company_crud.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CompanyServiceImplTest {

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CompanyMapper companyMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCompanies() {
        // Arrange
        Company company1 = new Company();
        company1.setId(1L);
        company1.setName("Company1");

        Company company2 = new Company();
        company2.setId(2L);
        company2.setName("Company2");

        List<Company> companies = Arrays.asList(company1, company2);

        when(companyRepository.findAll()).thenReturn(companies);

        CompanyDTO companyDTO1 = new CompanyDTO();
        companyDTO1.setId(1L);
        companyDTO1.setName("Company1");

        CompanyDTO companyDTO2 = new CompanyDTO();
        companyDTO2.setId(2L);
        companyDTO2.setName("Company2");

        when(companyMapper.companyToCompanyDTO(company1)).thenReturn(companyDTO1);
        when(companyMapper.companyToCompanyDTO(company2)).thenReturn(companyDTO2);

        // Act
        List<CompanyDTO> result = companyService.getAllCompanies();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Company1", result.get(0).getName());
        assertEquals("Company2", result.get(1).getName());

        verify(companyRepository, times(1)).findAll();
        verify(companyMapper, times(1)).companyToCompanyDTO(company1);
        verify(companyMapper, times(1)).companyToCompanyDTO(company2);
    }
    @Test
    void testGetCompanyById() {
        // Arrange
        Long companyId = 1L;

        Company company = new Company();
        company.setId(companyId);
        company.setName("Test Company");

        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));

        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(companyId);
        companyDTO.setName("Test Company");

        when(companyMapper.companyToCompanyDTO(company)).thenReturn(companyDTO);

        // Act
        CompanyDTO result = companyService.getCompanyById(companyId);

        // Assert
        assertNotNull(result);
        assertEquals(companyId, result.getId());
        assertEquals("Test Company", result.getName());

        verify(companyRepository, times(1)).findById(companyId);
        verify(companyMapper, times(1)).companyToCompanyDTO(company);
    }
    @Test
    void testCreateCompany() {
        // Arrange
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setName("New Company");

        Company company = new Company();
        company.setName("New Company");

        Company savedCompany = new Company();
        savedCompany.setId(1L);
        savedCompany.setName("New Company");

        CompanyDTO savedCompanyDTO = new CompanyDTO();
        savedCompanyDTO.setId(1L);
        savedCompanyDTO.setName("New Company");

        when(companyMapper.companyDTOToCompany(companyDTO)).thenReturn(company);
        when(companyRepository.save(company)).thenReturn(savedCompany);
        when(companyMapper.companyToCompanyDTO(savedCompany)).thenReturn(savedCompanyDTO);

        // Act
        CompanyDTO result = companyService.createCompany(companyDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Company", result.getName());

        verify(companyMapper, times(1)).companyDTOToCompany(companyDTO);
        verify(companyRepository, times(1)).save(company);
        verify(companyMapper, times(1)).companyToCompanyDTO(savedCompany);
    }

    @Test
    void testUpdateCompany() {
        // Arrange
        Long companyId = 1L;

        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setName("Updated Company");

        Company existingCompany = new Company();
        existingCompany.setId(companyId);
        existingCompany.setName("Old Company");

        Company updatedCompany = new Company();
        updatedCompany.setId(companyId);
        updatedCompany.setName("Updated Company");

        CompanyDTO updatedCompanyDTO = new CompanyDTO();
        updatedCompanyDTO.setId(companyId);
        updatedCompanyDTO.setName("Updated Company");

        when(companyRepository.findById(companyId)).thenReturn(Optional.of(existingCompany));
        when(companyRepository.save(existingCompany)).thenReturn(updatedCompany);
        when(companyMapper.companyToCompanyDTO(updatedCompany)).thenReturn(updatedCompanyDTO);

        // Act
        CompanyDTO result = companyService.updateCompany(companyId, companyDTO);

        // Assert
        assertNotNull(result);
        assertEquals(companyId, result.getId());
        assertEquals("Updated Company", result.getName());

        verify(companyRepository, times(1)).findById(companyId);
        verify(companyRepository, times(1)).save(existingCompany);
        verify(companyMapper, times(1)).companyToCompanyDTO(updatedCompany);
    }
    @Test
    void testDeleteCompany() {
        // Arrange
        Long companyId = 1L;

        // Act
        companyService.deleteCompany(companyId);

        // Assert
        verify(companyRepository, times(1)).deleteById(companyId);
    }
    @Test
    void testGetCompanyById_NotFound() {
        // Arrange
        Long companyId = 1L;

        when(companyRepository.findById(companyId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            companyService.getCompanyById(companyId);
        });

        assertEquals("Company not found", exception.getMessage());

        verify(companyRepository, times(1)).findById(companyId);
        verifyNoInteractions(companyMapper);
    }


}
