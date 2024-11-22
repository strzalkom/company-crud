package com.company.management.company_crud.service;

import com.company.management.company_crud.model.dto.ProjectDTO;

public interface ProjectService {
    ProjectDTO getProjectById(Long id);

    ProjectDTO createProject(Long teamId, ProjectDTO projectDTO);

    ProjectDTO updateProject(Long id, ProjectDTO projectDTO);

    void deleteProject(Long id);
}
