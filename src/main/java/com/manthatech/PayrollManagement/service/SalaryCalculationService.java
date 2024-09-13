package com.manthatech.PayrollManagement.service;

import com.manthatech.PayrollManagement.DTOS.SalaryCalculationResult;
import com.manthatech.PayrollManagement.model.*;
import com.manthatech.PayrollManagement.utils.SalaryConfiguration;
import com.manthatech.PayrollManagement.utils.WorkingDaysCalculator;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class SalaryCalculationService {

    private final WorkingDaysCalculator workingDaysCalculator;
    private final SalaryConfiguration salaryConfiguration;
    private final HolidayService holidayService;

    public SalaryCalculationService(WorkingDaysCalculator workingDaysCalculator,
                                    SalaryConfiguration salaryConfiguration,
                                    HolidayService holidayService) {
        this.workingDaysCalculator = workingDaysCalculator;
        this.salaryConfiguration = salaryConfiguration;
        this.holidayService = holidayService;
    }

    @Cacheable("workingDaysCache")
    public int getWorkingDays(LocalDate paymentDate) {
        List<LocalDate> holidays = holidayService.getHolidaysForMonth(paymentDate.getYear(), paymentDate.getMonth());
        return workingDaysCalculator.getWorkingDaysInMonth(paymentDate.getYear(), paymentDate.getMonth(), holidays);
    }

    public SalaryCalculationResult calculateSalary(Salary salary) {
        if (salary instanceof FullTimeSalary) {
            return calculateFullTimeSalary((FullTimeSalary) salary);
        } else {
            throw new IllegalArgumentException("Unsupported salary type: " + salary.getClass().getSimpleName());
        }
    }

    private SalaryCalculationResult calculateFullTimeSalary(FullTimeSalary salary) {
        BigDecimal baseSalary = calculateBaseSalary(salary);
        BigDecimal allowances = calculateAllowances(salary);
        BigDecimal benefits = calculateBenefits(salary);

        BigDecimal grossEarnings = baseSalary.add(allowances).add(benefits);
        BigDecimal lossOfPay = calculateLossOfPay(salary, grossEarnings);

        BigDecimal adjustedGrossSalary = grossEarnings.subtract(lossOfPay);
        BigDecimal deductions = calculateDeductions(salary, adjustedGrossSalary);
        BigDecimal netSalary = adjustedGrossSalary.subtract(deductions);

        return new SalaryCalculationResult(baseSalary, allowances, benefits, lossOfPay, deductions, adjustedGrossSalary, netSalary);
    }

    private BigDecimal calculateBaseSalary(FullTimeSalary salary) {
        return salary.getCustomBaseSalary() != null ?
                salary.getCustomBaseSalary() :
                salary.getSalaryStructure().getBaseSalary().multiply(salary.getBaseMultiplier());
    }

    private BigDecimal calculateLossOfPay(FullTimeSalary salary, BigDecimal grossEarnings) {
        int daysInMonth = getDaysForLOPCalculation(salary.getPaymentDate());
        BigDecimal dailySalary = grossEarnings.divide(BigDecimal.valueOf(daysInMonth), RoundingMode.HALF_UP);

        BigDecimal lopDays = salary.getLopDays();
        return dailySalary.multiply(lopDays);
    }

    private int getDaysForLOPCalculation(LocalDate paymentDate) {
        if (salaryConfiguration.isUseFixedDaysForLOP()) {
            return salaryConfiguration.getFixedDaysPerMonth();
        } else {
            return getWorkingDays(paymentDate);
        }
    }

    private BigDecimal calculateAllowances(FullTimeSalary salary) {
        Map<Allowance, BigDecimal> effectiveAllowances = new HashMap<>(
                salary.getSalaryStructure().getStructureAllowances().stream()
                        .collect(Collectors.toMap(StructureAllowance::getAllowance, StructureAllowance::getAmount))
        );

        salary.getCustomAllowances().forEach(ca ->
                effectiveAllowances.put(ca.getAllowance(), ca.getAmount())
        );

        return effectiveAllowances.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateBenefits(FullTimeSalary salary) {
        // Implement benefits calculation logic
        return BigDecimal.ZERO;  // Placeholder
    }

    private BigDecimal calculateDeductions(FullTimeSalary salary, BigDecimal adjustedGrossSalary) {
        Map<Deduction, BigDecimal> effectiveDeductions = new HashMap<>(
                salary.getSalaryStructure().getStructureDeductions().stream()
                        .collect(Collectors.toMap(StructureDeduction::getDeduction, StructureDeduction::getAmount))
        );

        salary.getCustomDeductions().forEach(cd ->
                effectiveDeductions.put(cd.getDeduction(), cd.getAmount())
        );

        // Calculate percentage-based deductions on adjusted gross salary
        effectiveDeductions.replaceAll((deduction, amount) ->
                deduction.isPercentageBased() ?
                        adjustedGrossSalary.multiply(amount.divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)) :
                        amount
        );

        return effectiveDeductions.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}




