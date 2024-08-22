package com.manthatech.PayrollManagement.service;

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

    public SalaryService<? extends Salary, ? extends SalaryDTO> getSalaryServiceById(Long id) {
        Salary salary = salaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Salary not found with id: " + id));

        if (salary instanceof FullTimeSalary) {
            return salaryServices.get("fullTimeSalaryService");
        }
       /* else if (salary instanceof PartTimeSalary) {
            return salaryServices.get("partTimeSalaryService");
        } else if (salary instanceof FreelanceSalary) {
            return salaryServices.get("freelanceSalaryService");
        }
       */

        throw new IllegalArgumentException("Unsupported salary type: " + salary.getClass().getSimpleName());
    }
}

