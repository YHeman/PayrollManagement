package com.manthatech.PayrollManagement.model;

import lombok.Getter;


@Getter
public enum EmployeeType {
    FULL_TIME("Full Time", FullTimeSalary.class),
    PART_TIME("Part Time", PartTimeSalary.class),
    CONTRACT("Contract", ContractSalary.class),
    INTERN("Intern", InternSalary.class),
    FREELANCE("Freelance", FreelanceSalary.class);

    private final String displayName;
    private final Class<? extends Salary> salaryClass;

    EmployeeType(String displayName, Class<? extends Salary> salaryClass) {
        this.displayName = displayName;
        this.salaryClass = salaryClass;
    }

    public static EmployeeType fromSalaryClass(Class<? extends Salary> salaryClass) {
        for (EmployeeType type : values()) {
            if (type.getSalaryClass().equals(salaryClass)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No EmployeeType found for salary class: " + salaryClass.getSimpleName());
    }
}
