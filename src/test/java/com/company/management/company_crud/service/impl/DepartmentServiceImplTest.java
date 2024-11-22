package com.company.management.company_crud.service.impl;

import com.company.management.company_crud.mapper.DepartmentMapper;
import com.company.management.company_crud.model.dao.Company;
import com.company.management.company_crud.model.dao.Department;
import com.company.management.company_crud.model.dto.DepartmentDTO;
import com.company.management.company_crud.repository.CompanyRepository;
import com.company.management.company_crud.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentServiceImplTest {

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private DepartmentMapper departmentMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDepartmentsByCompanyId() {
        // Arrange
        Long companyId = 1L;

        Company company = new Company();
        company.setId(companyId);

        Department department1 = new Department();
        department1.setId(1L);
        department1.setName("Department 1");
        department1.setCompany(company);

        Department department2 = new Department();
        department2.setId(2L);
        department2.setName("Department 2");
        department2.setCompany(company);

        List<Department> departments = Arrays.asList(department1, department2);

        DepartmentDTO departmentDTO1 = new DepartmentDTO();
        departmentDTO1.setId(1L);
        departmentDTO1.setName("Department 1");

        DepartmentDTO departmentDTO2 = new DepartmentDTO();
        departmentDTO2.setId(2L);
        departmentDTO2.setName("Department 2");

        when(departmentRepository.findAll()).thenReturn(departments);
        when(departmentMapper.departmentToDepartmentDTO(department1)).thenReturn(departmentDTO1);
        when(departmentMapper.departmentToDepartmentDTO(department2)).thenReturn(departmentDTO2);

        // Act
        List<DepartmentDTO> result = departmentService.getDepartmentsByCompanyId(companyId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Department 1", result.get(0).getName());
        assertEquals("Department 2", result.get(1).getName());

        verify(departmentRepository, times(1)).findAll();
        verify(departmentMapper, times(1)).departmentToDepartmentDTO(department1);
        verify(departmentMapper, times(1)).departmentToDepartmentDTO(department2);
    }
    @Test
    void testGetDepartmentById() {
        // Arrange
        Long departmentId = 1L;

        Department department = new Department();
        department.setId(departmentId);
        department.setName("Department");

        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(departmentId);
        departmentDTO.setName("Department");

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        when(departmentMapper.departmentToDepartmentDTO(department)).thenReturn(departmentDTO);

        // Act
        DepartmentDTO result = departmentService.getDepartmentById(departmentId);

        // Assert
        assertNotNull(result);
        assertEquals(departmentId, result.getId());
        assertEquals("Department", result.getName());

        verify(departmentRepository, times(1)).findById(departmentId);
        verify(departmentMapper, times(1)).departmentToDepartmentDTO(department);
    }
    @Test
    void testCreateDepartment() {
        // Arrange
        Long companyId = 1L;

        Company company = new Company();
        company.setId(companyId);

        Department department = new Department();
        department.setName("New Department");

        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setName("New Department");

        Department savedDepartment = new Department();
        savedDepartment.setId(1L);
        savedDepartment.setName("New Department");
        savedDepartment.setCompany(company);

        DepartmentDTO savedDepartmentDTO = new DepartmentDTO();
        savedDepartmentDTO.setId(1L);
        savedDepartmentDTO.setName("New Department");

        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));
        when(departmentMapper.departmentDTOToDepartment(departmentDTO)).thenReturn(department);
        when(departmentRepository.save(department)).thenReturn(savedDepartment);
        when(departmentMapper.departmentToDepartmentDTO(savedDepartment)).thenReturn(savedDepartmentDTO);

        // Act
        DepartmentDTO result = departmentService.createDepartment(companyId, departmentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Department", result.getName());

        verify(companyRepository, times(1)).findById(companyId);
        verify(departmentMapper, times(1)).departmentDTOToDepartment(departmentDTO);
        verify(departmentRepository, times(1)).save(department);
        verify(departmentMapper, times(1)).departmentToDepartmentDTO(savedDepartment);
    }
    @Test
    void testUpdateDepartment() {
        // Arrange
        Long departmentId = 1L;

        Department existingDepartment = new Department();
        existingDepartment.setId(departmentId);
        existingDepartment.setName("Old Department");

        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setName("Updated Department");

        Department updatedDepartment = new Department();
        updatedDepartment.setId(departmentId);
        updatedDepartment.setName("Updated Department");

        DepartmentDTO updatedDepartmentDTO = new DepartmentDTO();
        updatedDepartmentDTO.setId(departmentId);
        updatedDepartmentDTO.setName("Updated Department");

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(existingDepartment));
        when(departmentRepository.save(existingDepartment)).thenReturn(updatedDepartment);
        when(departmentMapper.departmentToDepartmentDTO(updatedDepartment)).thenReturn(updatedDepartmentDTO);

        // Act
        DepartmentDTO result = departmentService.updateDepartment(departmentId, departmentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(departmentId, result.getId());
        assertEquals("Updated Department", result.getName());

        verify(departmentRepository, times(1)).findById(departmentId);
        verify(departmentRepository, times(1)).save(existingDepartment);
        verify(departmentMapper, times(1)).departmentToDepartmentDTO(updatedDepartment);
    }
    @Test
    void testDeleteDepartment() {
        // Arrange
        Long departmentId = 1L;

        // Act
        departmentService.deleteDepartment(departmentId);

        // Assert
        verify(departmentRepository, times(1)).deleteById(departmentId);
    }
    @Test
    void testGetDepartmentById_NotFound() {
        Long departmentId = 1L;

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            departmentService.getDepartmentById(departmentId);
        });

        assertEquals("Department not found", exception.getMessage());
        verify(departmentRepository, times(1)).findById(departmentId);
    }

}
