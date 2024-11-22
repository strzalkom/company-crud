package com.company.management.company_crud.mapper;

import com.company.management.company_crud.model.dao.Company;
import com.company.management.company_crud.model.dao.Department;
import com.company.management.company_crud.model.dto.CompanyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CompanyMapper {


    @Mapping(source = "departments", target = "departmentNames", qualifiedByName = "mapDepartmentNames")
    CompanyDTO companyToCompanyDTO(Company company);

    @Mapping(target = "departments", ignore = true)
    Company companyDTOToCompany(CompanyDTO companyDTO);

    @Named("mapDepartmentNames")
    default List<String> mapDepartmentNames(List<Department> departments) {
        return departments != null
                ? departments.stream()
                .map(Department::getName)
                .collect(Collectors.toList())
                : null;
    }
}
