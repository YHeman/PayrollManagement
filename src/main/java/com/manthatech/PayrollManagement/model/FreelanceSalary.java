package com.manthatech.PayrollManagement.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class FreelanceSalary extends Salary {
    private BigDecimal projectRate;
    private BigDecimal hourlyRate;
    private BigDecimal hoursWorked;
    private boolean isProjectBased;

}
