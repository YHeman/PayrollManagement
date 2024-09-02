package com.manthatech.PayrollManagement.repository;

import com.manthatech.PayrollManagement.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
