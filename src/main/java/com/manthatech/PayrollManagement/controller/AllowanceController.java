package com.manthatech.PayrollManagement.controller;

import com.manthatech.PayrollManagement.DTOS.AllowanceDTO;
import com.manthatech.PayrollManagement.model.Allowance;
import com.manthatech.PayrollManagement.service.AllowanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/allowances")
public class AllowanceController {

    @Autowired
    private AllowanceService allowanceService;

    @PostMapping
    public ResponseEntity<Allowance> createAllowance(@RequestBody AllowanceDTO allowanceDTO) {
        Allowance allowance = allowanceService.createAllowance(allowanceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(allowance);
    }

    @GetMapping
    public ResponseEntity<List<AllowanceDTO>> getAllAllowances() {
        List<AllowanceDTO> allowances = allowanceService.getAllAllowances();
        return ResponseEntity.ok(allowances);
    }

    @PutMapping("/{allowanceId}")
    public ResponseEntity<Allowance> updateAllowance(@PathVariable Long allowanceId, @RequestBody AllowanceDTO allowanceDTO) {
        Allowance updatedAllowance = allowanceService.updateAllowance(allowanceId, allowanceDTO);
        return ResponseEntity.ok(updatedAllowance);
    }

    @DeleteMapping("/{allowanceId}")
    public ResponseEntity<Void> deleteAllowance(@PathVariable Long allowanceId) {
        allowanceService.deleteAllowance(allowanceId);
        return ResponseEntity.noContent().build();
    }


}
