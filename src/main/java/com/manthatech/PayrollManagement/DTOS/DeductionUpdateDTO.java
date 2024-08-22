package com.manthatech.PayrollManagement.DTOS;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DeductionUpdateDTO {

    private Long deductionId;
    private BigDecimal amount;
}
