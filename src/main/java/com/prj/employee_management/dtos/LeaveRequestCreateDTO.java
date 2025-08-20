package com.prj.employee_management.dtos;


import java.time.LocalDate;
import java.util.UUID;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import jakarta.validation.constraints.Size;

public record LeaveRequestCreateDTO(

    @NotNull(message = "reason is required")
    @Size(min = 2, max = 100, message = "min is 2 character and max is 50 character")
    String reason,


    @NotNull(message = "start Date is required")
    @PastOrPresent(message = "start date cshould be now or in the future")
    LocalDate startDate,

    @NotNull(message = "End Date is required")
    @PastOrPresent(message = "end date cannot be inthe futur")
    LocalDate endDate


) {
}