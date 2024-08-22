package com.manthatech.PayrollManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenefitTypeRepository extends JpaRepository<com.manthatech.PayrollManagement.model.BenefitType, Long> {
}
