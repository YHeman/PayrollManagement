package com.manthatech.PayrollManagement.DTOS;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class FullTimeSalaryDTO extends SalaryDTO{
    private Long salaryStructureId;
    private BigDecimal customBaseSalary;
    private BigDecimal baseMultiplier;
    private Set<EmployeeAllowanceDTO> customAllowances;
    private Set<EmployeeDeductionDTO> customDeductions;
    private Set<EmployeeBenefitDTO> applicableBenefits;
    private BigDecimal lopDays;

}
