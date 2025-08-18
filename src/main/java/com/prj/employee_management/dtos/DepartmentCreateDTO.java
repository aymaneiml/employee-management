package com.prj.employee_management.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DepartmentCreateDTO(

    @NotNull(message = "name is required")
    @Size(min = 2, max = 50, message = "min is 2 character and max is 50 character")
    String name
) {

}