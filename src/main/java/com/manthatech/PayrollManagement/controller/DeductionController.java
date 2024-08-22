package com.manthatech.PayrollManagement.controller;

import com.manthatech.PayrollManagement.DTOS.AllowanceDTO;
import com.manthatech.PayrollManagement.DTOS.DeductionDTO;
import com.manthatech.PayrollManagement.model.Deduction;
import com.manthatech.PayrollManagement.service.DeductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deductions")
public class DeductionController {
    @Autowired
    private DeductionService deductionService;

    @PostMapping
    public ResponseEntity<Deduction> createDeduction(@RequestBody DeductionDTO deductionDTO) {
        Deduction deduction = deductionService.createDeduction(deductionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(deduction);
    }

    @GetMapping
    public ResponseEntity<List<DeductionDTO>> getAllDeductions() {
        List<DeductionDTO> allowances = deductionService.getAllDeductions();
        return ResponseEntity.ok(allowances);
    }

    @PutMapping("/deductions/{deductionId}")
    public ResponseEntity<Deduction> updateDeduction(@PathVariable Long deductionId, @RequestBody DeductionDTO deductionDTO) {
        Deduction updatedDeduction = deductionService.updateDeduction(deductionId, deductionDTO);
        return ResponseEntity.ok(updatedDeduction);
    }

    @DeleteMapping("/deductions/{deductionId}")
    public ResponseEntity<Void> deleteDeduction(@PathVariable Long deductionId) {
        deductionService.deleteDeduction(deductionId);
        return ResponseEntity.noContent().build();
    }

}
