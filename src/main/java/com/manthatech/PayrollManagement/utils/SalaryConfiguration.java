package com.manthatech.PayrollManagement.utils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class SalaryConfiguration {
    private boolean useFixedDaysForLOP = true;
    private int fixedDaysPerMonth = 30;
}