package com.manthatech.PayrollManagement.service;


import com.manthatech.PayrollManagement.DTOS.SalaryCalculationResult;
import org.springframework.transaction.annotation.Transactional;
import com.manthatech.PayrollManagement.DTOS.SalaryDTO;
import com.manthatech.PayrollManagement.model.*;
import com.manthatech.PayrollManagement.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public abstract class BaseSalaryService<T extends Salary, D extends SalaryDTO> implements SalaryService<T, D>{

    @Autowired
    protected BaseSalaryRepository<T> baseSalaryRepository;

    @Autowired
    protected EmployeeRepository employeeRepository;

    @Autowired
    protected SalaryCalculationService salaryCalculationService;

    @Override
    public Optional<D> getSalaryById(Long id) {
        return baseSalaryRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<D> getAllSalaries() {
        return baseSalaryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<D> getSalariesByEmployeeId(Long employeeId) {
        return baseSalaryRepository.findByEmployeeEmployeeId(employeeId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteSalary(Long id) {
        baseSalaryRepository.deleteById(id);
    }

    public SalaryCalculationResult calculateSalary(Long salaryId) {
        T salary = baseSalaryRepository.findById(salaryId)
                .orElseThrow(() -> new EntityNotFoundException("Salary not found with id: " + salaryId));
        return salaryCalculationService.calculateSalary(salary);
    }


//    @Transactional(readOnly = true)
//    public BigDecimal calculateGrossSalary(Long salaryId) {
//        T salary = baseSalaryRepository.findById(salaryId)
//                .orElseThrow(() -> new EntityNotFoundException("Salary not found with id: " + salaryId));
//        return salaryCalculationService.calculateGrossSalary(salary);
//    }
//
//    @Transactional
//    public BigDecimal calculateNetSalary(Long salaryId) {
//        T salary = baseSalaryRepository.findById(salaryId)
//                .orElseThrow(() -> new EntityNotFoundException("Salary not found with id: " + salaryId));
//        return salaryCalculationService.calculateNetSalary(salary);
//    }

    protected void validateSalaryType(Employee employee, T salary) {
        EmployeeType expectedType = EmployeeType.fromSalaryClass(salary.getClass());
        if (employee.getEmployeeType() != expectedType) {
            throw new IllegalArgumentException("Mismatch between employee type and salary type");
        }
    }

    // Abstract methods to be implemented by subclasses
    protected abstract D convertToDTO(T salary);
    protected abstract T convertToEntity(D salaryDTO);

}
