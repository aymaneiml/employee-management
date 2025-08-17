package com.prj.employee_management.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EmployeeUpdateDTO(
    
    @NotNull(message = "first name is required")
    @Size(min = 2, max = 50, message = "min is 2 character and max is 50 character")
    String firstName,

    @NotNull(message = "last name is required")
    @Size(min = 2, max = 50, message = "min is 2 character and max is 50 character")
    String lastName,

    @NotNull(message = "phoneNumber is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$" , message= "Invalid phone number format")
    String phoneNumber,


    @NotNull(message = "position is required")
    @Size(min = 2, max = 50, message = "min is 2 character and max is 50 character")
    String position
) {
}