package com.company.management.company_crud.mapper;

import com.company.management.company_crud.model.dao.Project;
import com.company.management.company_crud.model.dto.ProjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(source = "team.name", target = "teamName")
    @Mapping(source = "manager.name", target = "managerName")
    ProjectDTO projectToProjectDTO(Project project);

    @Mapping(target = "team", ignore = true)
    @Mapping(target = "manager", ignore = true)
    Project projectDTOToProject(ProjectDTO projectDTO);
}
