package com.company.management.company_crud.service.impl;

import com.company.management.company_crud.mapper.TeamMapper;
import com.company.management.company_crud.model.dao.Department;
import com.company.management.company_crud.model.dao.Team;
import com.company.management.company_crud.model.dto.TeamDTO;
import com.company.management.company_crud.repository.DepartmentRepository;
import com.company.management.company_crud.repository.TeamRepository;
import com.company.management.company_crud.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final DepartmentRepository departmentRepository;
    private final TeamMapper teamMapper;

    @Override
    public List<TeamDTO> getTeamsByDepartmentId(Long departmentId) {
        return teamRepository.findAll()
                .stream()
                .filter(team -> team.getDepartment().getId().equals(departmentId))
                .map(teamMapper::teamToTeamDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TeamDTO getTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        return teamMapper.teamToTeamDTO(team);
    }

    @Override
    public TeamDTO createTeam(Long departmentId, TeamDTO teamDTO) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Team team = teamMapper.teamDTOToTeam(teamDTO);
        team.setDepartment(department);

        Team savedTeam = teamRepository.save(team);
        return teamMapper.teamToTeamDTO(savedTeam);
    }


    @Override
    public TeamDTO updateTeam(Long id, TeamDTO teamDTO) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        team.setName(teamDTO.getName());
        return teamMapper.teamToTeamDTO(teamRepository.save(team));
    }

    @Override
    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }
}