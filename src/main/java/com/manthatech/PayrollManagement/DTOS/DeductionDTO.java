package com.manthatech.PayrollManagement.DTOS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeductionDTO {
    private Long id;
    private String name;
    private String description;
    private boolean isStatutory;
    private boolean isMandatory;
    private long country_id;
}
