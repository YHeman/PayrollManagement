package com.manthatech.PayrollManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee_benefits")
public class EmployeeBenefit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "benefit_type_id", nullable = false)
    private BenefitType benefitType;

    private BigDecimal amount;
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
    private String frequency; // "ONE_TIME", "ANNUAL", "QUARTERLY", etc.
    private boolean isPaid;

}