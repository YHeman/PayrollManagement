package com.manthatech.PayrollManagement.DTOS;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class StructureDeductionDTO {
    private Long id;
    private Long deductionId;
    private BigDecimal amount;
}
