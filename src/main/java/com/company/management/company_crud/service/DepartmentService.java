package com.company.management.company_crud.service;

import com.company.management.company_crud.model.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDTO> getDepartmentsByCompanyId(Long companyId);

    DepartmentDTO getDepartmentById(Long id);

    DepartmentDTO createDepartment(Long companyId, DepartmentDTO departmentDTO);

    DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO);

    void deleteDepartment(Long id);
}
