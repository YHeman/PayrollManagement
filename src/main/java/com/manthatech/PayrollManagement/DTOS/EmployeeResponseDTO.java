package com.manthatech.PayrollManagement.DTOS;

import com.manthatech.PayrollManagement.model.EmployeeType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeResponseDTO {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private EmployeeType employeeType;
    private LocalDate hireDate;
    private boolean enabled;
    private Long currentSalaryId;
    private JobDTO job;
    private DepartmentDTO department;
    private CountryDTO country;
}
