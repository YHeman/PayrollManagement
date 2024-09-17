package com.manthatech.PayrollManagement.DTOS;

import com.manthatech.PayrollManagement.model.Allowance;
import com.manthatech.PayrollManagement.model.Deduction;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
public class PayslipDetails {
    private Long employeeId;
    private String employeeName;
    private String department;
    private String designation;
    private BigDecimal basicSalary;
    private BigDecimal lopDays;
    private int totalDays;
    private EmployeeSensitiveInfoDTO employeeSensitiveInfo;
    private Map<String, PayslipAllowance> allowances;
    private Map<String, PayslipDeduction> deductions;
    private LocalDate hireDate;
}
