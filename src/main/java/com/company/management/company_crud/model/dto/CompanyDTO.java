package com.company.management.company_crud.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    private List<String> departmentNames;
}
