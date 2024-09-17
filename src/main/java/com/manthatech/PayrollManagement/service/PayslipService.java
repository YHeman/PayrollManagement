package com.manthatech.PayrollManagement.service;

import com.manthatech.PayrollManagement.DTOS.EmployeeSensitiveInfoDTO;
import com.manthatech.PayrollManagement.DTOS.PayslipAllowance;
import com.manthatech.PayrollManagement.DTOS.PayslipDeduction;
import com.manthatech.PayrollManagement.DTOS.PayslipDetails;
import com.manthatech.PayrollManagement.model.*;
import com.manthatech.PayrollManagement.repository.*;
import com.manthatech.PayrollManagement.utils.SalaryConfiguration;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PayslipService {
    @Autowired
    private EmployeeRepository employeeRepository;


    @Autowired
    private SalaryConfiguration salaryConfiguration;




    public PayslipDetails getPayslipDetails(Long employeeId) {
        PayslipDetails dto = new PayslipDetails();
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee Not Found"));
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setEmployeeName(employee.getFirstName().concat(employee.getLastName()));
        dto.setDesignation(employee.getJob().getJobTitle());
        dto.setDepartment(employee.getDepartment().getDepartmentName());
        dto.setHireDate(employee.getHireDate());
        dto.setEmployeeSensitiveInfo(convertToDTO(employee.getSensitiveInfo()));

        FullTimeSalary salary = (FullTimeSalary) employee.getCurrentSalary();
        dto.setBasicSalary(calculateBaseSalary(salary));
        dto.setLopDays(salary.getLopDays());
        dto.setTotalDays(salaryConfiguration.getFixedDaysPerMonth());
        dto.setAllowances(getAllAllowances(salary));
        dto.setDeductions(getAllDeductions(salary));
        return dto;

    }

    private Map<String, PayslipAllowance> getAllAllowances(FullTimeSalary salary) {
        Map<String, PayslipAllowance> allowances = new HashMap<>();

        // Process structure allowances
        for (StructureAllowance sa : salary.getSalaryStructure().getStructureAllowances()) {
            allowances.put(sa.getAllowance().getName(), new PayslipAllowance(
                    sa.getAllowance().getName(),
                    sa.getAmount()
            ));
        }

        // Process custom allowances, overriding structure allowances
        salary.getCustomAllowances().forEach(ca -> {
            String name = ca.getAllowance().getName();
            if (allowances.containsKey(name)) {
                // If allowance already exists, update the amount
                PayslipAllowance existing = allowances.get(name);
                existing.setAmount(existing.getAmount().add(ca.getAmount()));
            } else {
                // If it's a new allowance, add it to the map
                allowances.put(name, new PayslipAllowance(
                        name,
                        ca.getAmount()
                ));
            }
        });

        return allowances;
    }

    private Map<String, PayslipDeduction> getAllDeductions(FullTimeSalary salary) {
        Map<String, PayslipDeduction> deductions = new HashMap<>();

        // Process structure deductions
        for (StructureDeduction sd : salary.getSalaryStructure().getStructureDeductions()) {
            deductions.put(sd.getDeduction().getName(), new PayslipDeduction(
                    sd.getDeduction().getName(),
                    sd.getAmount()
            ));
        }

        salary.getCustomDeductions().forEach(cd -> {
            String name = cd.getDeduction().getName();
            if (deductions.containsKey(name)) {
                PayslipDeduction existing = deductions.get(name);
                existing.setAmount(existing.getAmount().add(cd.getAmount()));
            } else {
                deductions.put(name, new PayslipDeduction(
                        name,
                        cd.getAmount()
                ));
            }
        });

        return deductions;
    }
    private BigDecimal calculateBaseSalary(FullTimeSalary salary) {
        return salary.getCustomBaseSalary() != null ?
                salary.getCustomBaseSalary() :
                salary.getSalaryStructure().getBaseSalary().multiply(salary.getBaseMultiplier());
    }

    public EmployeeSensitiveInfoDTO convertToDTO(EmployeeSensitiveInfo employeeSensitiveInfo) {
        EmployeeSensitiveInfoDTO employeeSensitiveInfoDTO = new EmployeeSensitiveInfoDTO();
        employeeSensitiveInfoDTO.setPan(employeeSensitiveInfo.getPan());
        employeeSensitiveInfoDTO.setAadhaarNumber(employeeSensitiveInfo.getAadhaarNumber());
        employeeSensitiveInfoDTO.setBankAccountNumber(employeeSensitiveInfo.getBankAccountNumber());
        employeeSensitiveInfoDTO.setIfscCode(employeeSensitiveInfo.getIfscCode());
        return employeeSensitiveInfoDTO;
    }
}