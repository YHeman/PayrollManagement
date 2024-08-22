package com.manthatech.PayrollManagement.repository;

import com.manthatech.PayrollManagement.model.PayrollRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PayrollRepository extends JpaRepository<PayrollRun, Long> {
}
