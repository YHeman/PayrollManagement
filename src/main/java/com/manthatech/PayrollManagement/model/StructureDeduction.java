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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "structure_deductions")
public class StructureDeduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "salary_structure_id")
    @JsonBackReference
    private SalaryStructure salaryStructure;

    @ManyToOne
    @JoinColumn(name = "deduction_id")
    private Deduction deduction;

    private BigDecimal amount;

}