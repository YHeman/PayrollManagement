package com.manthatech.PayrollManagement.service;


import org.springframework.transaction.annotation.Transactional;
import com.manthatech.PayrollManagement.DTOS.SalaryDTO;
import com.manthatech.PayrollManagement.model.*;
import com.manthatech.PayrollManagement.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public abstract class BaseSalaryService<T extends Salary, D extends SalaryDTO> {

    @Autowired
    protected SalaryRepository<T> salaryRepository;

    @Autowired
    protected EmployeeRepository employeeRepository;

    @Autowired
    protected SalaryCalculationService salaryCalculationService;

    @Transactional(readOnly = true)
    public Optional<T> getSalaryById(Long id) {
        return salaryRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<T> getAllSalaries() {
        return salaryRepository.findAll();
    }

    @Transactional
    public void deleteSalary(Long id) {
        salaryRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<T> getSalariesByEmployeeId(Long employeeId) {
        return salaryRepository.findByEmployeeEmployeeId(employeeId);
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateGrossSalary(Long salaryId) {
        T salary = getSalaryById(salaryId)
                .orElseThrow(() -> new EntityNotFoundException("Salary not found with id: " + salaryId));
        return salaryCalculationService.calculateGrossSalary(salary);
    }

    @Transactional
    public BigDecimal calculateNetSalary(Long salaryId) {
        T salary = getSalaryById(salaryId)
                .orElseThrow(() -> new EntityNotFoundException("Salary not found with id: " + salaryId));
        return salaryCalculationService.calculateNetSalary(salary);
    }

    protected void validateSalaryType(Employee employee, T salary) {
        EmployeeType expectedType = EmployeeType.fromSalaryClass(salary.getClass());
        if (employee.getEmployeeType() != expectedType) {
            throw new IllegalArgumentException("Mismatch between employee type and salary type");
        }
    }

    // Abstract methods to be implemented by subclasses
    protected abstract T convertToEntity(D salaryDTO);
    protected abstract D convertToDTO(T salary);

    @Transactional
    public abstract T createSalary(D salaryDTO);

    @Transactional
    public abstract T updateSalary(Long id, D salaryDTO);
}
