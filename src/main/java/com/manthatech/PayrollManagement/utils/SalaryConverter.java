package com.manthatech.PayrollManagement.utils;

import com.manthatech.PayrollManagement.DTOS.*;
import com.manthatech.PayrollManagement.model.*;

import java.util.stream.Collectors;
/*
public class SalaryConverter {
    public static FullTimeSalaryDTO convertToDto(Salary salary) {
        FullTimeSalaryDTO dto = new FullTimeSalaryDTO();
        dto.setId(salary.getId());
        dto.setEmployee(convertToDto(salary.getEmployee()));
        dto.setBasicSalary(salary.getBasicSalary());
        dto.setPaymentDate(salary.getPaymentDate());
        dto.setPaymentPeriod(salary.getPaymentPeriod());
        dto.setAllowances(salary.getCustomAllowances().stream()
                .map(SalaryConverter::convertToDto)
                .collect(Collectors.toSet()));
        dto.setDeductions(salary.getCustomDeductions().stream()
                .map(SalaryConverter::convertToDto)
                .collect(Collectors.toSet()));
        dto.setGrossSalary(salary.getGrossSalary());
        dto.setNetSalary(salary.getNetSalary());
        return dto;
    }

    public static EmployeeDTO convertToDto(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());
        dto.setHireDate(employee.getHireDate());
        dto.setStatus(employee.getStatus());
        return dto;
    }

    public static SalaryAllowanceDTO convertToDto(EmployeeAllowance employeeAllowance) {
        SalaryAllowanceDTO dto = new SalaryAllowanceDTO();
        dto.setId(employeeAllowance.getId());
        dto.setAllowance(convertToDto(employeeAllowance.getAllowance()));
        dto.setAmount(employeeAllowance.getAmount());
        return dto;
    }

    public static AllowanceDTO convertToDto(Allowance allowance) {
        AllowanceDTO dto = new AllowanceDTO();
        dto.setId(allowance.getId());
        dto.setName(allowance.getName());
        dto.setDescription(allowance.getDescription());
        dto.setTaxable(allowance.isTaxable());
        return dto;
    }

    public static SalaryDeductionDTO convertToDto(EmployeeDeduction employeeDeduction) {
        SalaryDeductionDTO dto = new SalaryDeductionDTO();
        dto.setId(employeeDeduction.getId());
        dto.setDeduction(convertToDto(employeeDeduction.getDeduction()));
        dto.setAmount(employeeDeduction.getAmount());
        return dto;
    }

    public static DeductionDTO convertToDto(Deduction deduction) {
        DeductionDTO dto = new DeductionDTO();
        dto.setId(deduction.getId());
        dto.setName(deduction.getName());
        dto.setDescription(deduction.getDescription());
        dto.setStatutory(deduction.isStatutory());
        return dto;
    }
}
*/