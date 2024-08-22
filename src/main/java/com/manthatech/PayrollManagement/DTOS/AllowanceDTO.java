package com.manthatech.PayrollManagement.DTOS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllowanceDTO {
    private Long id;
    private String name;
    private String description;
    private boolean isTaxable;
    private boolean isMandatory;
}
