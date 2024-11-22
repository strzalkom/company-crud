package com.company.management.company_crud.controller;

import com.company.management.company_crud.model.dto.ManagerDTO;
import com.company.management.company_crud.service.ManagerService;
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

@WebMvcTest(controllers = ManagerController.class)
class ManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManagerService managerService;

    @BeforeEach
    void setUp() {
        Mockito.reset(managerService);
    }

    @Test
    void testGetManagerById() throws Exception {
        // Arrange
        Long managerId = 1L;

        ManagerDTO manager = new ManagerDTO();
        manager.setId(managerId);
        manager.setName("Mateusz Strzałko");
        manager.setEmail("mateusz.strzalko@test.com");

        when(managerService.getManagerById(managerId)).thenReturn(manager);

        // Act & Assert
        mockMvc.perform(get("/api/managers/{id}", managerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(managerId))
                .andExpect(jsonPath("$.name").value("Mateusz Strzałko"))
                .andExpect(jsonPath("$.email").value("mateusz.strzalko@test.com"));

        verify(managerService, times(1)).getManagerById(managerId);
    }
    @Test
    void testCreateManager() throws Exception {
        // Arrange
        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.setName("Mateusz Strzałko");
        managerDTO.setEmail("mateusz.strzalko@test.com");

        ManagerDTO savedManager = new ManagerDTO();
        savedManager.setId(1L);
        savedManager.setName("Mateusz Strzałko");
        savedManager.setEmail("mateusz.strzalko@test.com");

        when(managerService.createManager(any(ManagerDTO.class))).thenReturn(savedManager);

        // Act & Assert
        mockMvc.perform(post("/api/managers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Mateusz Strzałko\", \"email\": \"mateusz.strzalko@test.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Mateusz Strzałko"))
                .andExpect(jsonPath("$.email").value("mateusz.strzalko@test.com"));

        verify(managerService, times(1)).createManager(any(ManagerDTO.class));
    }
    @Test
    void testUpdateManager() throws Exception {
        // Arrange
        Long managerId = 1L;

        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.setName("Updated Name");
        managerDTO.setEmail("updated.email@example.com");

        ManagerDTO updatedManager = new ManagerDTO();
        updatedManager.setId(managerId);
        updatedManager.setName("Updated Name");
        updatedManager.setEmail("updated.email@example.com");

        when(managerService.updateManager(eq(managerId), any(ManagerDTO.class))).thenReturn(updatedManager);

        // Act & Assert
        mockMvc.perform(put("/api/managers/{id}", managerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Name\", \"email\": \"updated.email@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(managerId))
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.email").value("updated.email@example.com"));

        verify(managerService, times(1)).updateManager(eq(managerId), any(ManagerDTO.class));
    }
    @Test
    void testDeleteManager() throws Exception {
        // Arrange
        Long managerId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/managers/{id}", managerId))
                .andExpect(status().isNoContent());

        verify(managerService, times(1)).deleteManager(managerId);
    }


}
