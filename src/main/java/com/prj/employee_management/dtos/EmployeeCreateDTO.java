package com.prj.employee_management.dtos;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EmployeeCreateDTO(

    @NotNull(message = "first name is required")
    @Size(min = 2, max = 50, message = "min is 2 character and max is 50 character")
    String firstName,

    @NotNull(message = "last name is required")
    @Size(min = 2, max = 50, message = "min is 2 character and max is 50 character")
    String lastName,

    @NotNull(message = "last name is required")
    @Email(message = "Invalid email format")
    String email,

    @NotNull(message = "phoneNumber is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$" , message= "Invalid phone number format")
    String phoneNumber,

    @NotNull(message = "hireDate is required")
    @PastOrPresent(message = "Hire date cannot be inthe futur")
    LocalDate hireDate,

    @NotNull(message = "position is required")
    @Size(min = 2, max = 50, message = "min is 2 character and max is 50 character")
    String position,

    @NotNull(message = "department id is required")
    UUID departmentId
    
) {
}