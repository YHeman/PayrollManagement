package com.manthatech.PayrollManagement.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class FullTimeSalary extends Salary {
    private BigDecimal customBaseSalary;
    private BigDecimal baseMultiplier = BigDecimal.ONE;

    @ManyToOne
    @JoinColumn(name = "salary_structure_id")
    private SalaryStructure salaryStructure;

    @OneToMany(mappedBy = "salary", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<EmployeeAllowance> customAllowances = new HashSet<>();

    @OneToMany(mappedBy = "salary", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<EmployeeDeduction> customDeductions = new HashSet<>();

    private BigDecimal lopDays;

    public void setLopDays(BigDecimal lopDays) {
        BigDecimal half = new BigDecimal("0.5");
        BigDecimal[] result = lopDays.divideAndRemainder(half);
        if(result[1].compareTo(BigDecimal.ZERO) == 0) {
            this.lopDays = lopDays;
        }
        else this.lopDays = lopDays.round(new MathContext(1));
    }
}
