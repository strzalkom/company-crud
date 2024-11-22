package com.company.management.company_crud.service.impl;

import com.company.management.company_crud.mapper.DepartmentMapper;
import com.company.management.company_crud.model.dao.Company;
import com.company.management.company_crud.model.dao.Department;
import com.company.management.company_crud.model.dto.DepartmentDTO;
import com.company.management.company_crud.repository.CompanyRepository;
import com.company.management.company_crud.repository.DepartmentRepository;
import com.company.management.company_crud.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public List<DepartmentDTO> getDepartmentsByCompanyId(Long companyId) {
        return departmentRepository.findAll()
                .stream()
                .filter(department -> department.getCompany().getId().equals(companyId))
                .map(departmentMapper::departmentToDepartmentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        return departmentMapper.departmentToDepartmentDTO(department);
    }

    @Override
    public DepartmentDTO createDepartment(Long companyId, DepartmentDTO departmentDTO) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        Department department = departmentMapper.departmentDTOToDepartment(departmentDTO);
        department.setCompany(company);
        return departmentMapper.departmentToDepartmentDTO(departmentRepository.save(department));
    }

    @Override
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        department.setName(departmentDTO.getName());
        return departmentMapper.departmentToDepartmentDTO(departmentRepository.save(department));
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}