package com.company.management.company_crud.service.impl;

import com.company.management.company_crud.mapper.ProjectMapper;
import com.company.management.company_crud.model.dao.Project;
import com.company.management.company_crud.model.dao.Team;
import com.company.management.company_crud.model.dto.ProjectDTO;
import com.company.management.company_crud.repository.ProjectRepository;
import com.company.management.company_crud.repository.TeamRepository;
import com.company.management.company_crud.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return projectMapper.projectToProjectDTO(project);
    }

    @Override
    public ProjectDTO createProject(Long teamId, ProjectDTO projectDTO) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        Project project = projectMapper.projectDTOToProject(projectDTO);
        project.setTeam(team);
        return projectMapper.projectToProjectDTO(projectRepository.save(project));
    }

    @Override
    public ProjectDTO updateProject(Long id, ProjectDTO projectDTO) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        project.setName(projectDTO.getName());
        return projectMapper.projectToProjectDTO(projectRepository.save(project));
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}