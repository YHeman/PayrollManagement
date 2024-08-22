package com.manthatech.PayrollManagement.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class PartTimeSalary extends Salary {
    private BigDecimal hourlyRate;
    private BigDecimal hoursWorked;

}