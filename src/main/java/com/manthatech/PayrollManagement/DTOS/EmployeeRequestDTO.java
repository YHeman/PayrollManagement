package com.manthatech.PayrollManagement.DTOS;

import com.manthatech.PayrollManagement.model.EmployeeType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeRequestDTO {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private EmployeeType employeeType;
    private LocalDate hireDate;
    private boolean enabled;
    private Long currentSalaryId;
    private Long jobId;
    private Long departmentId;
    private Long countryId;
}
