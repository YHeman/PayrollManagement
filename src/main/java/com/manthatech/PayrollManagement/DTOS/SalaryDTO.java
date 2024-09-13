package com.manthatech.PayrollManagement.DTOS;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = FullTimeSalaryDTO.class, name = "fulltime")
        // Add more types here as needed separated with comma
})
@Getter
@Setter
public abstract class SalaryDTO {
    private Long id;
    private Long employeeId;
    private LocalDate paymentDate;
    private String paymentPeriod;
}
