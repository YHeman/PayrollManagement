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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "allowances")
public class Allowance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private boolean isTaxable;
    private boolean isMandatory;

    @OneToMany(mappedBy = "allowance")
    @JsonIgnore
    private Set<StructureAllowance> structureAllowances = new HashSet<>();

    @OneToMany(mappedBy = "allowance")
    @JsonIgnore
    private Set<EmployeeAllowance> employeeAllowances = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

}
