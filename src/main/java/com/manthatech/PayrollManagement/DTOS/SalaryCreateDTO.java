package com.manthatech.PayrollManagement.DTOS;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
public class SalaryCreateDTO {
    private Long employeeId;
    private BigDecimal basicSalary;
    private Map<Long, BigDecimal> deductions;
    private Map<Long, BigDecimal> allowances;
    private LocalDate paymentDate;
    private String paymentPeriod;
}
