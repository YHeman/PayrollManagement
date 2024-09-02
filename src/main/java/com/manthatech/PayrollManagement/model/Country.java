package com.manthatech.PayrollManagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String country;

    @OneToMany(mappedBy = "country")
    @JsonBackReference
    private Set<Allowance> countryAllowances = new HashSet<>();

    @OneToMany(mappedBy = "country")
    @JsonBackReference
    private Set<Deduction> countryDeductions = new HashSet<>();

    @OneToMany(mappedBy = "country")
    @JsonBackReference
    private Set<Employee> countryEmployees = new HashSet<>();

    @OneToMany(mappedBy = "country")
    @JsonBackReference
    private List<SalaryStructure> countrySalaryStructures = new ArrayList<>();

}
