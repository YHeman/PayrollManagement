package com.manthatech.PayrollManagement.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class InternSalary extends Salary {
    private BigDecimal stipendAmount;
}
