package com.company.management.company_crud.service;

import com.company.management.company_crud.model.dto.ManagerDTO;

public interface ManagerService {
    ManagerDTO getManagerById(Long id);

    ManagerDTO createManager(ManagerDTO managerDTO);

    ManagerDTO updateManager(Long id, ManagerDTO managerDTO);

    void deleteManager(Long id);
}
