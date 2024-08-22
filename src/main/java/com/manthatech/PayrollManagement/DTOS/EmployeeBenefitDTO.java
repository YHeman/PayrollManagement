package com.manthatech.PayrollManagement.DTOS;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class EmployeeBenefitDTO {
    private Long id;
    private Long benefitTypeId;
    private BigDecimal amount;
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
    private String frequency; // "ONE_TIME", "ANNUAL", "QUARTERLY", etc.
    private boolean isPaid;

}
