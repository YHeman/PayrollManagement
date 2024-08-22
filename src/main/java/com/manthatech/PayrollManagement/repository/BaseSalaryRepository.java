package com.manthatech.PayrollManagement.repository;

import com.manthatech.PayrollManagement.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface SalaryRepository<T extends Salary> extends JpaRepository<T, Long> {
    List<T> findByEmployeeEmployeeId(Long employeeId);

}


