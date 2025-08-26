package com.prj.employee_management.dtos;


import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignupRequestDTO(
    
    @NotNull(message ="username is required")
    @Size(min = 2, max = 50, message = "min is 2 characters and max is 50 characters")
    String username,

    @NotNull(message ="password is required")
    @Size(min = 2, max = 50, message = "min is 2 characters and max is 50 characters")
    String password


) {
}