package com.manthatech.PayrollManagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "salaries")
public abstract class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonBackReference
    private Employee employee;

    private LocalDate paymentDate;
    private String paymentPeriod;

//    @ManyToOne
//    @JoinColumn(name = "salary_structure_id")
//    private SalaryStructure salaryStructure;

//    private BigDecimal customBaseSalary;
//    private BigDecimal baseMultiplier = BigDecimal.ONE;


//    @OneToMany(mappedBy = "salary", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    private Set<EmployeeAllowance> customAllowances = new HashSet<>();
//
//    @OneToMany(mappedBy = "salary", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    private Set<EmployeeDeduction> customDeductions = new HashSet<>();

    @Transient
    private List<EmployeeBenefit> applicableBenefits = new ArrayList<>();


//    private BigDecimal grossSalary;
//    private BigDecimal netSalary;


//    public BigDecimal calculateGrossSalary() {
//        BigDecimal baseSalary = (customBaseSalary != null) ? customBaseSalary
//                : salaryStructure.getBaseSalary().multiply(baseMultiplier);
//
//        BigDecimal allowancesTotal = calculateAllowancesTotal();
//        BigDecimal benefitsTotal = calculateBenefitsTotal();
//
//        return baseSalary.add(allowancesTotal).add(benefitsTotal);
//    }
//
//    public BigDecimal calculateNetSalary() {
//        BigDecimal grossSalary = calculateGrossSalary();
//        BigDecimal deductionsTotal = calculateDeductionsTotal();
//
//        return grossSalary.subtract(deductionsTotal);
//    }
//
//    private BigDecimal calculateAllowancesTotal() {
//        Map<Allowance, BigDecimal> effectiveAllowances = new HashMap<>();
//
//        for (StructureAllowance sa : salaryStructure.getStructureAllowances()) {
//            effectiveAllowances.put(sa.getAllowance(), sa.getAmount());
//        }
//
//        for (EmployeeAllowance ca : customAllowances) {
//            effectiveAllowances.put(ca.getAllowance(), ca.getAmount());
//        }
//
//        return effectiveAllowances.values().stream()
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
//
//    private BigDecimal calculateDeductionsTotal() {
//        Map<Deduction, BigDecimal> effectiveDeductions = new HashMap<>();
//
//        // First, add all structure deductions
//        for (StructureDeduction sd : salaryStructure.getStructureDeductions()) {
//            effectiveDeductions.put(sd.getDeduction(), sd.getAmount());
//        }
//
//        // Then, override with custom deductions or add new ones
//        for (EmployeeDeduction cd : customDeductions) {
//            effectiveDeductions.put(cd.getDeduction(), cd.getAmount());
//        }
//
//        // Sum up all effective deductions
//        return effectiveDeductions.values().stream()
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
//
//    private BigDecimal calculateBenefitsTotal() {
//        return applicableBenefits.stream()
//                .map(EmployeeBenefit::getAmount)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }

}