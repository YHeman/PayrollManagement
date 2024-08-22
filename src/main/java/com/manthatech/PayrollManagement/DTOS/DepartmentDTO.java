package com.manthatech.PayrollManagement.DTOS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentDTO {
    private Long departmentId;
    private String departmentName;
    private String location;
    private Long managerId;
}
