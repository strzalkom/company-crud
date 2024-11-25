package com.company.management.company_crud.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    private String teamName;
    private String managerName;
}
