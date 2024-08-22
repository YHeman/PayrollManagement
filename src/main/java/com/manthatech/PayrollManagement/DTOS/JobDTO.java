package com.manthatech.PayrollManagement.DTOS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobDTO {
    private Long jobId;
    private String jobTitle;
    private String description;
}
