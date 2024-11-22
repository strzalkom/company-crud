package com.company.management.company_crud.service.impl;

import com.company.management.company_crud.mapper.ManagerMapper;
import com.company.management.company_crud.model.dao.Manager;
import com.company.management.company_crud.model.dto.ManagerDTO;
import com.company.management.company_crud.repository.ManagerRepository;
import com.company.management.company_crud.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;
    private final ManagerMapper managerMapper;

    @Override
    public ManagerDTO getManagerById(Long id) {
        Manager manager = managerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        return managerMapper.managerToManagerDTO(manager);
    }

    @Override
    public ManagerDTO createManager(ManagerDTO managerDTO) {
        Manager manager = managerMapper.managerDTOToManager(managerDTO);
        return managerMapper.managerToManagerDTO(managerRepository.save(manager));
    }

    @Override
    public ManagerDTO updateManager(Long id, ManagerDTO managerDTO) {
        Manager manager = managerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        manager.setName(managerDTO.getName());
        manager.setEmail(managerDTO.getEmail());
        return managerMapper.managerToManagerDTO(managerRepository.save(manager));
    }

    @Override
    public void deleteManager(Long id) {
        managerRepository.deleteById(id);
    }
}