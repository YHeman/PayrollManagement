package com.manthatech.PayrollManagement.controller;

import com.manthatech.PayrollManagement.DTOS.PayslipDetails;
import com.manthatech.PayrollManagement.service.PayslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payslips")
@CrossOrigin
public class PayslipController {

    @Autowired
    private PayslipService payslipService;

    @GetMapping("/{employeeId}")
    public ResponseEntity<PayslipDetails> getPayslipDetails(@PathVariable Long employeeId) {
        return ResponseEntity.ok(payslipService.getPayslipDetails(employeeId));
    }
}