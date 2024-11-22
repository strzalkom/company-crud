package com.company.management.company_crud.service.impl;

import com.company.management.company_crud.mapper.ManagerMapper;
import com.company.management.company_crud.model.dao.Manager;
import com.company.management.company_crud.model.dto.ManagerDTO;
import com.company.management.company_crud.repository.ManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManagerServiceImplTest {

    @InjectMocks
    private ManagerServiceImpl managerService;

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private ManagerMapper managerMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetManagerById() {
        // Arrange
        Long managerId = 1L;

        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setName("Mateusz Strzałko");
        manager.setEmail("mateusz.strzalko@test.com");

        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.setId(managerId);
        managerDTO.setName("Mateusz Strzałko");
        managerDTO.setEmail("mateusz.strzalko@test.com");

        when(managerRepository.findById(managerId)).thenReturn(Optional.of(manager));
        when(managerMapper.managerToManagerDTO(manager)).thenReturn(managerDTO);

        // Act
        ManagerDTO result = managerService.getManagerById(managerId);

        // Assert
        assertNotNull(result);
        assertEquals(managerId, result.getId());
        assertEquals("Mateusz Strzałko", result.getName());
        assertEquals("mateusz.strzalko@test.com", result.getEmail());

        verify(managerRepository, times(1)).findById(managerId);
        verify(managerMapper, times(1)).managerToManagerDTO(manager);
    }
    @Test
    void testGetManagerById_NotFound() {
        // Arrange
        Long managerId = 1L;

        when(managerRepository.findById(managerId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            managerService.getManagerById(managerId);
        });

        assertEquals("Manager not found", exception.getMessage());
        verify(managerRepository, times(1)).findById(managerId);
        verifyNoInteractions(managerMapper);
    }
    @Test
    void testCreateManager() {
        // Arrange
        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.setName("Mateusz Strzałko");
        managerDTO.setEmail("mateusz.strzalko@test.com");

        Manager manager = new Manager();
        manager.setName("Mateusz Strzałko");
        manager.setEmail("mateusz.strzalko@test.com");

        Manager savedManager = new Manager();
        savedManager.setId(1L);
        savedManager.setName("Mateusz Strzałko");
        savedManager.setEmail("mateusz.strzalko@test.com");

        ManagerDTO savedManagerDTO = new ManagerDTO();
        savedManagerDTO.setId(1L);
        savedManagerDTO.setName("Mateusz Strzałko");
        savedManagerDTO.setEmail("mateusz.strzalko@test.com");

        when(managerMapper.managerDTOToManager(managerDTO)).thenReturn(manager);
        when(managerRepository.save(manager)).thenReturn(savedManager);
        when(managerMapper.managerToManagerDTO(savedManager)).thenReturn(savedManagerDTO);

        // Act
        ManagerDTO result = managerService.createManager(managerDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Mateusz Strzałko", result.getName());
        assertEquals("mateusz.strzalko@test.com", result.getEmail());

        verify(managerMapper, times(1)).managerDTOToManager(managerDTO);
        verify(managerRepository, times(1)).save(manager);
        verify(managerMapper, times(1)).managerToManagerDTO(savedManager);
    }
    @Test
    void testUpdateManager() {
        // Arrange
        Long managerId = 1L;

        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.setName("Updated Name");
        managerDTO.setEmail("updated.email@example.com");

        Manager existingManager = new Manager();
        existingManager.setId(managerId);
        existingManager.setName("Old Name");
        existingManager.setEmail("old.email@example.com");

        Manager updatedManager = new Manager();
        updatedManager.setId(managerId);
        updatedManager.setName("Updated Name");
        updatedManager.setEmail("updated.email@example.com");

        ManagerDTO updatedManagerDTO = new ManagerDTO();
        updatedManagerDTO.setId(managerId);
        updatedManagerDTO.setName("Updated Name");
        updatedManagerDTO.setEmail("updated.email@example.com");

        when(managerRepository.findById(managerId)).thenReturn(Optional.of(existingManager));
        when(managerRepository.save(existingManager)).thenReturn(updatedManager);
        when(managerMapper.managerToManagerDTO(updatedManager)).thenReturn(updatedManagerDTO);

        // Act
        ManagerDTO result = managerService.updateManager(managerId, managerDTO);

        // Assert
        assertNotNull(result);
        assertEquals(managerId, result.getId());
        assertEquals("Updated Name", result.getName());
        assertEquals("updated.email@example.com", result.getEmail());

        verify(managerRepository, times(1)).findById(managerId);
        verify(managerRepository, times(1)).save(existingManager);
        verify(managerMapper, times(1)).managerToManagerDTO(updatedManager);
    }
    @Test
    void testDeleteManager() {
        // Arrange
        Long managerId = 1L;

        // Act
        managerService.deleteManager(managerId);

        // Assert
        verify(managerRepository, times(1)).deleteById(managerId);
    }

}
