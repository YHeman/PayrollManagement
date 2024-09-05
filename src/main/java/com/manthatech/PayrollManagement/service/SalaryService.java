package com.manthatech.PayrollManagement.service;

import com.manthatech.PayrollManagement.DTOS.SalaryCalculationResult;
import com.manthatech.PayrollManagement.DTOS.SalaryDTO;
import com.manthatech.PayrollManagement.model.Salary;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SalaryService<T extends Salary, D extends SalaryDTO> {
    Optional<D> getSalaryById(Long id);
    List<D> getAllSalaries();
    D createSalary(D salaryDTO);
    D updateSalary(Long id, D salaryDTO);
    void deleteSalary(Long id);
    List<D> getSalariesByEmployeeId(Long employeeId);
    SalaryCalculationResult calculateSalary(Long id);
//    BigDecimal calculateGrossSalary(Long id);
//    BigDecimal calculateNetSalary(Long id);
}
