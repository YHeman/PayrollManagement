package com.manthatech.PayrollManagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee_deductions")
public class EmployeeDeduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "salary_id")
    @JsonBackReference
    private Salary salary;

    @ManyToOne
    @JoinColumn(name = "deduction_id")
    private Deduction deduction;

    private BigDecimal amount;


}
