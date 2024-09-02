package com.manthatech.PayrollManagement.DTOS;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;


@Setter
@Getter
public class SalaryStructureDTO {
    private Long id;
    private String name;
    private BigDecimal baseSalary;
    private Set<StructureAllowanceDTO> structureAllowances;
    private Set<StructureDeductionDTO> structureDeductions;
    private Long countryId;
}

