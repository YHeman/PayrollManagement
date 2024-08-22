package com.manthatech.PayrollManagement.DTOS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeSensitiveInfoDTO {
    private String pan;
    private String bankAccountNumber;
    private String bankName;
    private String ifscCode;
    private String aadhaarNumber;
}
