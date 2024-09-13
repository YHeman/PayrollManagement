package com.manthatech.PayrollManagement.service;

import com.manthatech.PayrollManagement.DTOS.EmployeeAllowanceDTO;
import com.manthatech.PayrollManagement.DTOS.EmployeeDeductionDTO;
import com.manthatech.PayrollManagement.model.*;
import com.manthatech.PayrollManagement.DTOS.FullTimeSalaryDTO;
import com.manthatech.PayrollManagement.repository.AllowanceRepository;
import com.manthatech.PayrollManagement.repository.DeductionRepository;
import com.manthatech.PayrollManagement.repository.FullTimeSalaryRepository;
import com.manthatech.PayrollManagement.repository.SalaryStructureRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Service("fullTImeSalaryService")
public class FullTimeSalaryService extends BaseSalaryService<FullTimeSalary, FullTimeSalaryDTO> {

    @Autowired
    private SalaryStructureRepository salaryStructureRepository;

    @Autowired
    private AllowanceRepository allowanceRepository;

    @Autowired
    private DeductionRepository deductionRepository;

    @Autowired
    private FullTimeSalaryRepository fullTimeSalaryRepository;

    @Override
    @Transactional
    public FullTimeSalaryDTO createSalary(FullTimeSalaryDTO salaryDTO) {
        FullTimeSalary salary = convertToEntity(salaryDTO);

        Employee employee = employeeRepository.findById(salaryDTO.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        salary.setEmployee(employee);

        SalaryStructure salaryStructure = salaryStructureRepository.findById(salaryDTO.getSalaryStructureId())
                .orElseThrow(() -> new EntityNotFoundException("Salary structure not found"));
        salary.setSalaryStructure(salaryStructure);

        if(salaryDTO.getCustomAllowances() != null)  setCustomAllowances(salary, salaryDTO.getCustomAllowances());
        if(salaryDTO.getCustomDeductions() != null) setCustomDeductions(salary, salaryDTO.getCustomDeductions());

//        validateSalaryType(employee, salary);

        return convertToDTO(fullTimeSalaryRepository.save(salary));
    }

    @Override
    @Transactional
    public FullTimeSalaryDTO updateSalary(Long id, FullTimeSalaryDTO salaryDTO) {
        FullTimeSalary salary = fullTimeSalaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Salary not found"));

        if (salaryDTO.getCustomBaseSalary() != null) salary.setCustomBaseSalary(salaryDTO.getCustomBaseSalary());
        if (salaryDTO.getBaseMultiplier() != null) salary.setBaseMultiplier(salaryDTO.getBaseMultiplier());
        if (salaryDTO.getPaymentDate() != null) salary.setPaymentDate(salaryDTO.getPaymentDate());
        if (salaryDTO.getPaymentPeriod() != null) salary.setPaymentPeriod(salaryDTO.getPaymentPeriod());
        if (salaryDTO.getSalaryStructureId() != null) {
            SalaryStructure salaryStructure = salaryStructureRepository.findById(salaryDTO.getSalaryStructureId())
                    .orElseThrow(() -> new EntityNotFoundException("Salary structure not found"));
            salary.setSalaryStructure(salaryStructure);
        }

        if(salaryDTO.getCustomAllowances() != null) {
            salary.getCustomAllowances().clear();
            setCustomAllowances(salary, salaryDTO.getCustomAllowances());
        }
        if(salaryDTO.getCustomDeductions() != null) {
            salary.getCustomDeductions().clear();
            setCustomDeductions(salary, salaryDTO.getCustomDeductions());
        }

//        validateSalaryType(salary.getEmployee(), salary);

        return convertToDTO(fullTimeSalaryRepository.save(salary));
    }

    private void setCustomAllowances(FullTimeSalary salary, Set<EmployeeAllowanceDTO> allowanceDTOs) {
        for (EmployeeAllowanceDTO allowanceDTO : allowanceDTOs) {
            EmployeeAllowance employeeAllowance = new EmployeeAllowance();
            employeeAllowance.setSalary(salary);
            employeeAllowance.setAllowance(allowanceRepository.findById(allowanceDTO.getAllowanceId())
                    .orElseThrow(() -> new EntityNotFoundException("Allowance not found")));
            employeeAllowance.setAmount(allowanceDTO.getAmount());
            salary.getCustomAllowances().add(employeeAllowance);
        }
    }

    private void setCustomDeductions(FullTimeSalary salary, Set<EmployeeDeductionDTO> deductionDTOs) {
        for (EmployeeDeductionDTO deductionDTO : deductionDTOs) {
            EmployeeDeduction employeeDeduction = new EmployeeDeduction();
            employeeDeduction.setSalary(salary);
            employeeDeduction.setDeduction(deductionRepository.findById(deductionDTO.getDeductionId())
                    .orElseThrow(() -> new EntityNotFoundException("Deduction not found")));
            employeeDeduction.setAmount(deductionDTO.getAmount());
            salary.getCustomDeductions().add(employeeDeduction);
        }
    }

    @Override
    public FullTimeSalary convertToEntity(FullTimeSalaryDTO salaryDTO) {
        FullTimeSalary salary = new FullTimeSalary();
        if(salaryDTO.getCustomBaseSalary() != null) salary.setCustomBaseSalary(salaryDTO.getCustomBaseSalary());
        if(salaryDTO.getBaseMultiplier() != null) salary.setBaseMultiplier(salaryDTO.getBaseMultiplier());
        else salary.setBaseMultiplier(BigDecimal.ONE);
        if (salaryDTO.getLopDays() != null) salary.setLopDays(salaryDTO.getLopDays());
        salary.setPaymentDate(salaryDTO.getPaymentDate());
        salary.setPaymentPeriod(salaryDTO.getPaymentPeriod());
        return salary;
    }

    @Override
    public FullTimeSalaryDTO convertToDTO(FullTimeSalary salary) {
        FullTimeSalaryDTO dto = new FullTimeSalaryDTO();
        dto.setId(salary.getId());
        dto.setEmployeeId(salary.getEmployee().getEmployeeId());
        dto.setSalaryStructureId(salary.getSalaryStructure().getId());
        dto.setCustomBaseSalary(salary.getCustomBaseSalary());
        dto.setBaseMultiplier(salary.getBaseMultiplier());
        dto.setPaymentDate(salary.getPaymentDate());
        dto.setPaymentPeriod(salary.getPaymentPeriod());
        dto.setCustomAllowances(salary.getCustomAllowances().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toSet()));
        dto.setCustomDeductions(salary.getCustomDeductions().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toSet()));
        dto.setLopDays(salary.getLopDays());

        return dto;
    }

    private EmployeeAllowanceDTO convertToDTO(EmployeeAllowance employeeAllowance) {
        EmployeeAllowanceDTO dto = new EmployeeAllowanceDTO();
        dto.setId(employeeAllowance.getId());
        dto.setAmount(employeeAllowance.getAmount());
        dto.setAllowanceId(employeeAllowance.getAllowance().getId());
        return dto;
    }

    private EmployeeDeductionDTO convertToDTO(EmployeeDeduction employeeDeduction) {
        EmployeeDeductionDTO dto = new EmployeeDeductionDTO();
        dto.setId(employeeDeduction.getId());
        dto.setAmount(employeeDeduction.getAmount());
        dto.setDeductionId(employeeDeduction.getDeduction().getId());
        return dto;
    }
}