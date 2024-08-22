package com.manthatech.PayrollManagement.repository;

import com.manthatech.PayrollManagement.model.EmployeeSensitiveInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeSensitiveInfoRepository extends JpaRepository<EmployeeSensitiveInfo, Long> {
}
