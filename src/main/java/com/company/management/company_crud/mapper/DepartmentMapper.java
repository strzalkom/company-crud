package com.company.management.company_crud.mapper;

import com.company.management.company_crud.model.dao.Department;
import com.company.management.company_crud.model.dao.Team;
import com.company.management.company_crud.model.dto.DepartmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {


    @Mapping(source = "company.name", target = "companyName")
    @Mapping(source = "teams", target = "teamNames", qualifiedByName = "mapTeamNames")
    DepartmentDTO departmentToDepartmentDTO(Department department);


    @Mapping(target = "company", ignore = true)
    @Mapping(target = "teams", ignore = true)
    Department departmentDTOToDepartment(DepartmentDTO departmentDTO);


    @Named("mapTeamNames")
    default List<String> mapTeamNames(List<Team> teams) {
        return teams != null
                ? teams.stream()
                .map(Team::getName)
                .collect(Collectors.toList())
                : null;
    }
}
