package com.manthatech.PayrollManagement.service;

import com.manthatech.PayrollManagement.DTOS.FullTimeSalaryDTO;
import com.manthatech.PayrollManagement.DTOS.SalaryDTO;
import com.manthatech.PayrollManagement.model.FullTimeSalary;
import com.manthatech.PayrollManagement.model.Salary;
import com.manthatech.PayrollManagement.repository.SalaryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SalaryServiceAggregator {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private Map<String, SalaryService<? extends Salary, ? extends SalaryDTO>> salaryServices;

    public SalaryService<?, ? extends SalaryDTO> getSalaryServiceById(Long id) {
        Salary salary = salaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Salary not found with id: " + id));

        return getSalaryServiceByType(salary);
    }

    public SalaryService<?, SalaryDTO> getSalaryServiceByDTO(SalaryDTO salaryDTO) {
        if (salaryDTO instanceof FullTimeSalaryDTO) {
            return (SalaryService<?, SalaryDTO>) salaryServices.get("fullTimeSalaryService");
        }
        // more types

        throw new IllegalArgumentException("Unsupported salary DTO type: " + salaryDTO.getClass().getSimpleName());
    }

    private SalaryService<?, ? extends SalaryDTO> getSalaryServiceByType(Salary salary) {
        if (salary instanceof FullTimeSalary) {
            return salaryServices.get("fullTimeSalaryService");
        }
        // more services

        throw new IllegalArgumentException("Unsupported salary type: " + salary.getClass().getSimpleName());
    }

    public SalaryService<?, ? extends SalaryDTO> getDefaultSalaryService() {
        // Return a default service, e.g., the FullTimeSalaryService
        return salaryServices.get("fullTimeSalaryService");
    }
}

