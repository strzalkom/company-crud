package com.company.management.company_crud.model.dto;

import lombok.Data;

@Data
public class TeamDTO {
    private Long id;
    private String name;
    private String departmentName;
    private String projectName;
}
