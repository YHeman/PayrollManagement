package com.manthatech.PayrollManagement.service;

import com.manthatech.PayrollManagement.model.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@Service
public class SalaryCalculationService {

    public BigDecimal calculateGrossSalary(Salary salary) {
        if (salary instanceof FullTimeSalary) {
            return calculateFullTimeGrossSalary((FullTimeSalary) salary);
        } else if (salary instanceof PartTimeSalary) {
            return calculatePartTimeGrossSalary((PartTimeSalary) salary);
        } else if (salary instanceof FreelanceSalary) {
            return calculateFreelanceGrossSalary((FreelanceSalary) salary);
        } else {
            throw new IllegalArgumentException("Unsupported salary type: " + salary.getClass().getSimpleName());
        }
    }

    public BigDecimal calculateNetSalary(Salary salary) {
        if (salary instanceof FullTimeSalary) {
            return calculateFullTimeNetSalary((FullTimeSalary) salary);
        } else if (salary instanceof PartTimeSalary) {
            return calculatePartTimeNetSalary((PartTimeSalary) salary);
        } else if (salary instanceof FreelanceSalary) {
            return calculateFreelanceNetSalary((FreelanceSalary) salary);
        } else {
            throw new IllegalArgumentException("Unsupported salary type: " + salary.getClass().getSimpleName());
        }
    }

    private BigDecimal calculateFullTimeGrossSalary(FullTimeSalary salary) {
        BigDecimal baseSalary = salary.getCustomBaseSalary() != null ?
                salary.getCustomBaseSalary() :
                salary.getSalaryStructure().getBaseSalary().multiply(salary.getBaseMultiplier());

        BigDecimal allowances = calculateAllowances(salary);
        BigDecimal benefits = calculateBenefits(salary);

        return baseSalary.add(allowances).add(benefits);
    }

    private BigDecimal calculateFullTimeNetSalary(FullTimeSalary salary) {
        BigDecimal grossSalary = calculateFullTimeGrossSalary(salary);
        BigDecimal totalDeductions = calculateTotalDeductions(salary);

        return grossSalary.subtract(totalDeductions);
    }

    private BigDecimal calculatePartTimeGrossSalary(PartTimeSalary salary) {
        //part-time salary calculation logic
        return null;  // Placeholder
    }

    private BigDecimal calculatePartTimeNetSalary(PartTimeSalary salary) {
        // part-time net salary calculation logic
        return null;  // Placeholder
    }

    private BigDecimal calculateFreelanceGrossSalary(FreelanceSalary salary) {
        // freelance salary calculation logic
        return null;  // Placeholder
    }

    private BigDecimal calculateFreelanceNetSalary(FreelanceSalary salary) {
        // freelance net salary calculation logic
        return null;  // Placeholder
    }

    private BigDecimal calculateAllowances(FullTimeSalary salary) {
        Map<Allowance, BigDecimal> effectiveAllowances = new HashMap<>();

        for (StructureAllowance sa : salary.getSalaryStructure().getStructureAllowances()) {
            effectiveAllowances.put(sa.getAllowance(), sa.getAmount());
        }

        for (EmployeeAllowance ca : salary.getCustomAllowances()) {
            effectiveAllowances.put(ca.getAllowance(), ca.getAmount());
        }

        return effectiveAllowances.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateBenefits(FullTimeSalary salary) {
        // benefits calculation logic
        return BigDecimal.ZERO;  // Placeholder
    }

    private BigDecimal calculateTotalDeductions(FullTimeSalary salary) {
        Map<Deduction, BigDecimal> effectiveDeductions = new HashMap<>();

        // First, add all structure deductions
        for (StructureDeduction sd : salary.getSalaryStructure().getStructureDeductions()) {
            effectiveDeductions.put(sd.getDeduction(), sd.getAmount());
        }

        // Then, override with custom deductions or add new ones
        for (EmployeeDeduction cd : salary.getCustomDeductions()) {
            effectiveDeductions.put(cd.getDeduction(), cd.getAmount());
        }

        // Sum up all effective deductions
        return effectiveDeductions.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

