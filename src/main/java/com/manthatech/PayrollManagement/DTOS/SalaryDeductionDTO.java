package com.manthatech.PayrollManagement.DTOS;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class SalaryDeductionDTO {
    private Long id;
    private DeductionDTO deduction;
    private BigDecimal amount;
}
