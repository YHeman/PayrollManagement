package com.manthatech.PayrollManagement.DTOS;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class SalaryAllowanceDTO {
    private Long id;
    private AllowanceDTO allowance;
    private BigDecimal amount;
}
