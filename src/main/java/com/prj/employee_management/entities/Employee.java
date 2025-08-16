package com.prj.employee_management.entities;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    
    private UUID id;

    @NotNull(message = "first name is required")
    @Size(min = 2, max = 50, message = "min is 2 character and max is 50 character")
    private String firstName;

    @NotNull(message = "last name is required")
    @Size(min = 2, max = 50, message = "min is 2 character and max is 50 character")
    private String lastName;

    @NotNull(message = "last name is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "phoneNumber is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$" , message= "Invalid phone number format")
    private String phoneNumber;

    @NotNull(message = "hireDate is required")
    @PastOrPresent(message = "Hire date cannot be inthe futur")
    private LocalDate hireDate;

    @NotNull(message = "position is required")
    @Size(min = 2, max = 50, message = "min is 2 character and max is 50 character")
    private String position;

    private UUID departementId;

}