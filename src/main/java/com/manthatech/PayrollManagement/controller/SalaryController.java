package com.manthatech.PayrollManagement.controller;

import com.manthatech.PayrollManagement.DTOS.SalaryDTO;
import com.manthatech.PayrollManagement.model.Salary;
import com.manthatech.PayrollManagement.service.SalaryService;
import com.manthatech.PayrollManagement.service.SalaryServiceAggregator;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/salaries")
public class SalaryController {

    @Autowired
    private Map<String, SalaryService<? extends Salary, ? extends SalaryDTO>> salaryServices;

    @Autowired
    private SalaryServiceAggregator salaryServiceAggregator;



    private SalaryService<? extends Salary, ? extends SalaryDTO> getSalaryService(String type) {
        SalaryService<? extends Salary, ? extends SalaryDTO> service = salaryServices.get(type.toLowerCase() + "SalaryService");
        if (service == null) {
            throw new IllegalArgumentException("Unsupported salary type: " + type);
        }
        return service;
    }

    @SuppressWarnings("unchecked")
    private <D extends SalaryDTO> SalaryService<? extends Salary, D> getTypedSalaryService(String type) {
        return (SalaryService<? extends Salary, D>) getSalaryService(type);
    }


    @PostMapping("/{type}")
    public <D extends SalaryDTO> ResponseEntity<D> createSalary(@PathVariable String type, @RequestBody D salaryDTO) {
        SalaryService<? extends Salary, D> service = getTypedSalaryService(type);
        D createdSalary = service.createSalary(salaryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSalary);
    }

    @PutMapping("/{type}/{id}")
    public <D extends SalaryDTO> ResponseEntity<D> updateSalary(@PathVariable String type, @PathVariable Long id, @RequestBody D salaryDTO) {
        SalaryService<? extends Salary, D> service = getTypedSalaryService(type);
        D updatedSalary = service.updateSalary(id, salaryDTO);
        return ResponseEntity.ok(updatedSalary);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSalaryById(@PathVariable Long id) {
        SalaryService<? extends Salary, ? extends SalaryDTO> service = salaryServiceAggregator.getSalaryServiceById(id);
        return service.getSalaryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @GetMapping("/{type}/{id}")
//    public <D extends SalaryDTO> ResponseEntity<D> getSalaryById(@PathVariable String type, @PathVariable Long id) {
//        SalaryService<? extends Salary, D> service = getTypedSalaryService(type);
//        return service.getSalaryById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

    @GetMapping("/{type}")
    public <D extends SalaryDTO> ResponseEntity<List<D>> getAllSalaries(@PathVariable String type) {
        SalaryService<? extends Salary, D> service = getTypedSalaryService(type);
        List<D> salaryDTOs = service.getAllSalaries();
        return ResponseEntity.ok(salaryDTOs);
    }

    @GetMapping("/{type}/employee/{employeeId}")
    public <D extends SalaryDTO> ResponseEntity<List<D>> getSalariesByEmployeeId(@PathVariable String type, @PathVariable Long employeeId) {
        SalaryService<? extends Salary, D> service = getTypedSalaryService(type);
        List<D> salaryDTOs = service.getSalariesByEmployeeId(employeeId);
        return ResponseEntity.ok(salaryDTOs);
    }


    @DeleteMapping("/{type}/{id}")
    public ResponseEntity<Void> deleteSalary(@PathVariable String type, @PathVariable Long id) {
        SalaryService<? extends Salary, ? extends SalaryDTO> service = getSalaryService(type);
        service.deleteSalary(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/{type}/{id}/gross")
    public ResponseEntity<BigDecimal> calculateGrossSalary(@PathVariable String type, @PathVariable Long id) {
        SalaryService<? extends Salary, ? extends SalaryDTO> service = getSalaryService(type);
        BigDecimal grossSalary = service.calculateGrossSalary(id);
        return ResponseEntity.ok(grossSalary);
    }

    @GetMapping("/{type}/{id}/net")
    public ResponseEntity<BigDecimal> calculateNetSalary(@PathVariable String type, @PathVariable Long id) {
        SalaryService<? extends Salary, ? extends SalaryDTO> service = getSalaryService(type);
        BigDecimal netSalary = service.calculateNetSalary(id);
        return ResponseEntity.ok(netSalary);
    }

    @PostConstruct
    public void init() {
        System.out.println("Autowired SalaryServices: " + salaryServices.keySet());
    }
}