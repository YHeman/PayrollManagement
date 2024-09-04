package com.manthatech.PayrollManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "deductions")
public class Deduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private boolean isStatutory;
    private boolean isMandatory;
    private boolean isPercentageBased;

    @OneToMany(mappedBy = "deduction")
    @JsonIgnore
    private Set<StructureDeduction> structureDeductions = new HashSet<>();

    @OneToMany(mappedBy = "deduction")
    @JsonIgnore
    private Set<EmployeeDeduction> employeeDeductions = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

}
