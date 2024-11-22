package com.company.management.company_crud.mapper;

import com.company.management.company_crud.model.dao.Team;
import com.company.management.company_crud.model.dto.TeamDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    @Mapping(source = "department.name", target = "departmentName")
    TeamDTO teamToTeamDTO(Team team);

    @Mapping(target = "department", ignore = true)
    Team teamDTOToTeam(TeamDTO teamDTO);
}