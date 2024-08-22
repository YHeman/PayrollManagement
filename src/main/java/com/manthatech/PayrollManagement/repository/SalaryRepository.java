package com.manthatech.PayrollManagement.repository;

import com.manthatech.PayrollManagement.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
    // You can add common queries here if needed
}

