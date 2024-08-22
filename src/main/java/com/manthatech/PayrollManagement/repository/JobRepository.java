package com.manthatech.PayrollManagement.repository;

import com.manthatech.PayrollManagement.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
}
