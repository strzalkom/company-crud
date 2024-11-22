package com.company.management.company_crud.service;

import com.company.management.company_crud.model.dto.TeamDTO;

import java.util.List;

public interface TeamService {
    List<TeamDTO> getTeamsByDepartmentId(Long departmentId);

    TeamDTO getTeamById(Long id);

    TeamDTO createTeam(Long departmentId, TeamDTO teamDTO);

    TeamDTO updateTeam(Long id, TeamDTO teamDTO);

    void deleteTeam(Long id);
}