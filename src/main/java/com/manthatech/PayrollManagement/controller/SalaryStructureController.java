package com.manthatech.PayrollManagement.controller;

import com.manthatech.PayrollManagement.DTOS.SalaryStructureDTO;
import com.manthatech.PayrollManagement.service.SalaryStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salary-structures")
public class SalaryStructureController {

    @Autowired
    private SalaryStructureService salaryStructureService;

    @PostMapping
    public ResponseEntity<SalaryStructureDTO> createSalaryStructure(@RequestBody SalaryStructureDTO salaryStructureDTO) {
        SalaryStructureDTO createdStructure = salaryStructureService.createSalaryStructure(salaryStructureDTO);
        return new ResponseEntity<>(createdStructure, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SalaryStructureDTO>> getAllStructures() {
        return new ResponseEntity<>(salaryStructureService.getAllStructures(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaryStructureDTO> updateSalaryStructure(
            @PathVariable Long id,
            @RequestBody SalaryStructureDTO salaryStructureDTO) {
        SalaryStructureDTO updatedStructure = salaryStructureService.updateSalaryStructure(id, salaryStructureDTO);
        return new ResponseEntity<>(updatedStructure, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<SalaryStructureDTO> getSalaryStructure(@PathVariable Long id) {
        SalaryStructureDTO salaryStructureDTO = salaryStructureService.getSalaryStructure(id);
        return new ResponseEntity<>(salaryStructureDTO, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalaryStructure(@PathVariable Long id) {
        salaryStructureService.deleteSalaryStructure(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
