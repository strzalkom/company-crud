package com.company.management.company_crud.mapper;

import com.company.management.company_crud.model.dao.Manager;
import com.company.management.company_crud.model.dto.ManagerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ManagerMapper {


    ManagerDTO managerToManagerDTO(Manager manager);


    Manager managerDTOToManager(ManagerDTO managerDTO);
}
