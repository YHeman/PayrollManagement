package com.manthatech.PayrollManagement.DTOS;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public abstract class SalaryDTO {
    private Long id;
    private Long employeeId;
    private LocalDate paymentDate;
    private String paymentPeriod;
}
