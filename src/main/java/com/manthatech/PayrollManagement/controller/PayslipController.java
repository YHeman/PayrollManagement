package com.manthatech.PayrollManagement.controller;

import com.manthatech.PayrollManagement.model.Employee;
import com.manthatech.PayrollManagement.model.Salary;
import com.manthatech.PayrollManagement.repository.EmployeeRepository;
import com.manthatech.PayrollManagement.repository.SalaryRepository;
import com.manthatech.PayrollManagement.service.PayslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class PayslipController {

    @Autowired
    private PayslipService payslipService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SalaryRepository salaryRepository;

    @GetMapping("/payslip/{employeeId}/{salaryId}")
    public ResponseEntity<byte[]> generatePayslip(@PathVariable Long employeeId, @PathVariable Long salaryId) {
        try {
            Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
            Salary salary = salaryRepository.findById(salaryId).orElseThrow(() -> new RuntimeException("Salary not found"));

            byte[] pdfBytes = payslipService.generatePayslip(employee, salary);

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, "application/pdf");
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=payslip.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
