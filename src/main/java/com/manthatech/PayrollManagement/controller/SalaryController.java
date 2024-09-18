package com.manthatech.PayrollManagement.controller;

import com.manthatech.PayrollManagement.DTOS.FullTimeSalaryDTO;
import com.manthatech.PayrollManagement.DTOS.SalaryCalculationResult;
import com.manthatech.PayrollManagement.DTOS.SalaryDTO;
import com.manthatech.PayrollManagement.service.BaseSalaryService;
import com.manthatech.PayrollManagement.service.FullTimeSalaryService;
import com.manthatech.PayrollManagement.service.SalaryService;
import com.manthatech.PayrollManagement.service.SalaryServiceAggregator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/api/salaries")
public class SalaryController {

    @Autowired
    private SalaryServiceAggregator salaryServiceAggregator;

    @Autowired
    private FullTimeSalaryService fullTimeSalaryService;

    @GetMapping
    public ResponseEntity<List<FullTimeSalaryDTO>> getAllSalaries() {
        List<FullTimeSalaryDTO> salaryDTOS = fullTimeSalaryService.getAllSalaries();
        return new ResponseEntity<>(salaryDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<? extends SalaryDTO> getSalaryById(@PathVariable Long id) {
        FullTimeSalaryDTO salaryDTO = fullTimeSalaryService.getSalaryById(id).orElseThrow(EntityNotFoundException::new);
        return new ResponseEntity<>(salaryDTO, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<SalaryDTO> createSalary(@RequestBody FullTimeSalaryDTO salaryDTO) {
        SalaryDTO createdSalary = fullTimeSalaryService.createSalary(salaryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSalary);
    }

    @GetMapping("/{id}/calculate-salary")
    public ResponseEntity<SalaryCalculationResult> calculateSalary(@PathVariable Long id) {
        SalaryCalculationResult result = fullTimeSalaryService.calculateSalary(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaryDTO> updateSalary(@PathVariable Long id, @RequestBody FullTimeSalaryDTO salaryDTO) {
        SalaryDTO updatedSalary = fullTimeSalaryService.updateSalary(id, salaryDTO);
        return ResponseEntity.ok(updatedSalary);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalary(@PathVariable Long id) {
        fullTimeSalaryService.deleteSalary(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<SalaryDTO>> getSalariesByEmployeeId(@PathVariable Long employeeId) {
        List<? extends SalaryDTO> salaries = fullTimeSalaryService.getSalariesByEmployeeId(employeeId);
        return ResponseEntity.ok((List<SalaryDTO>) salaries);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<? extends SalaryDTO> getSalaryById(@PathVariable Long id) {
//        SalaryService<?, ? extends SalaryDTO> service = salaryServiceAggregator.getSalaryServiceById(id);
//        return service.getSalaryById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

//    @PostMapping
//    public ResponseEntity<SalaryDTO> createSalary(@RequestBody SalaryDTO salaryDTO) {
//        SalaryService<?, SalaryDTO> service = salaryServiceAggregator.getSalaryServiceByDTO(salaryDTO);
//        SalaryDTO createdSalary = service.createSalary(salaryDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdSalary);
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<SalaryDTO> updateSalary(@PathVariable Long id, @RequestBody SalaryDTO salaryDTO) {
//        SalaryService<?, SalaryDTO> service = salaryServiceAggregator.getSalaryServiceByDTO(salaryDTO);
//        SalaryDTO updatedSalary = service.updateSalary(id, salaryDTO);
//        return ResponseEntity.ok(updatedSalary);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteSalary(@PathVariable Long id) {
//        SalaryService<?, ?> service = salaryServiceAggregator.getSalaryServiceById(id);
//        service.deleteSalary(id);
//        return ResponseEntity.noContent().build();
//    }

//    @GetMapping("/employee/{employeeId}")
//    public ResponseEntity<List<SalaryDTO>> getSalariesByEmployeeId(@PathVariable Long employeeId) {
//        // Assuming all salary types are handled the same way, default set to fullTimeSalary
//        SalaryService<?, ? extends SalaryDTO> service = salaryServiceAggregator.getDefaultSalaryService();
//        List<? extends SalaryDTO> salaries = service.getSalariesByEmployeeId(employeeId);
//        return ResponseEntity.ok((List<SalaryDTO>) salaries);
//    }

//    public ResponseEntity<SalaryCalculationResult> calculateSalary(@PathVariable Long id) {
//        SalaryService<?, ?> service = salaryServiceAggregator.getSalaryServiceById(id);
//        SalaryCalculationResult salary = service.calculateSalary(id);
//        return ResponseEntity.ok(salary);
//    }

//    @GetMapping("/{id}/gross")
//    public ResponseEntity<BigDecimal> calculateGrossSalary(@PathVariable Long id) {
//        SalaryService<?, ?> service = salaryServiceAggregator.getSalaryServiceById(id);
//        BigDecimal grossSalary = service.calculateGrossSalary(id);
//        return ResponseEntity.ok(grossSalary);
//    }
//
//    @GetMapping("/{id}/net")
//    public ResponseEntity<BigDecimal> calculateNetSalary(@PathVariable Long id) {
//        SalaryService<?, ?> service = salaryServiceAggregator.getSalaryServiceById(id);
//        BigDecimal netSalary = service.calculateNetSalary(id);
//        return ResponseEntity.ok(netSalary);
//    }
}