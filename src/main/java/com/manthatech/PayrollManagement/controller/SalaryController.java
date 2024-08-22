package com.manthatech.PayrollManagement.controller;

import com.manthatech.PayrollManagement.DTOS.*;
import com.manthatech.PayrollManagement.model.FullTimeSalary;
import com.manthatech.PayrollManagement.model.Salary;
import com.manthatech.PayrollManagement.service.BaseSalaryService;
import com.manthatech.PayrollManagement.service.FullTimeSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/salaries")
public class SalaryController {

    @Autowired
    private FullTimeSalaryService fullTimeSalaryService;

    @PostMapping("/fulltime")
    public ResponseEntity<SalaryDTO> createFullTimeSalary(@RequestBody FullTimeSalaryDTO salaryDTO) {
        FullTimeSalary createdSalary = fullTimeSalaryService.createSalary(salaryDTO);
        return ResponseEntity.ok(fullTimeSalaryService.convertToDTO(createdSalary));
    }

    @PutMapping("/fulltime/{id}")
    public ResponseEntity<SalaryDTO> updateFullTimeSalary(@PathVariable Long id, @RequestBody FullTimeSalaryDTO salaryDTO) {
        FullTimeSalary updatedSalary = fullTimeSalaryService.updateSalary(id, salaryDTO);
        return ResponseEntity.ok(fullTimeSalaryService.convertToDTO(updatedSalary));
    }

    // Common endpoints for all salary types
    @GetMapping("/{id}")
    public ResponseEntity<SalaryDTO> getSalaryById(@PathVariable Long id) {
        // You might need to determine which service to use based on the salary type
        // For simplicity, let's use FullTimeSalaryService as an example
        return fullTimeSalaryService.getSalaryById(id)
                .map(salary -> ResponseEntity.ok(fullTimeSalaryService.convertToDTO(salary)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalary(@PathVariable Long id) {
        // Similar to getSalaryById, you might need to determine which service to use
        fullTimeSalaryService.deleteSalary(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<SalaryDTO>> getSalariesByEmployeeId(@PathVariable Long employeeId) {
        // You might need to aggregate results from all salary types
        List<FullTimeSalary> salaries = fullTimeSalaryService.getSalariesByEmployeeId(employeeId);
        List<SalaryDTO> salaryDTOs = salaries.stream()
                .map(fullTimeSalaryService::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(salaryDTOs);
    }

    @GetMapping("/{id}/gross")
    public ResponseEntity<BigDecimal> calculateGrossSalary(@PathVariable Long id) {
        // You might need to determine which service to use based on the salary type
        BigDecimal grossSalary = fullTimeSalaryService.calculateGrossSalary(id);
        return ResponseEntity.ok(grossSalary);
    }

    @GetMapping("/{id}/net")
    public ResponseEntity<BigDecimal> calculateNetSalary(@PathVariable Long id) {
        // You might need to determine which service to use based on the salary type
        BigDecimal netSalary = fullTimeSalaryService.calculateNetSalary(id);
        return ResponseEntity.ok(netSalary);
    }


}