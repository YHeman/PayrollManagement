package com.manthatech.PayrollManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Allowance allowance;

    @OneToMany(mappedBy = "country")
    private Employee employee;

    @OneToMany(mappedBy = "country")
    private Deduction deduction;

    @OneToMany(mappedBy = "country")
    private SalaryStructure salaryStructure;

}
