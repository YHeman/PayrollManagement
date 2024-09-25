package com.manthatech.PayrollManagement.controller;

import com.manthatech.PayrollManagement.DTOS.PayslipDetails;
import com.manthatech.PayrollManagement.service.PayslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payslips")
@CrossOrigin
public class PayslipController {

    @Autowired
    private PayslipService payslipService;
    @GetMapping("/{employeeId}/{lopdays}/generate-payslip")
    public ResponseEntity<Void> generatePayslip(@PathVariable Long employeeId, @PathVariable BigDecimal lopdays) {
        payslipService.generatePayslip(employeeId, lopdays);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        try {
            payslipService.generateBulkPayslips(file);
            return ResponseEntity.ok("Payslips generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file.");
        }
    }

    @GetMapping("/{employeeId}/generate-payslip")
    public ResponseEntity<Void> generatePayslip(@PathVariable Long employeeId) {
        payslipService.generatePayslip(employeeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<PayslipDetails> getPayslipDetails(@PathVariable Long employeeId) {
        return ResponseEntity.ok(payslipService.getPayslipDetails(employeeId));
    }
}