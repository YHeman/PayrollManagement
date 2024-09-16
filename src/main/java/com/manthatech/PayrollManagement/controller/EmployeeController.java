package com.manthatech.PayrollManagement.controller;

import com.manthatech.PayrollManagement.DTOS.EmployeeDTO;
import com.manthatech.PayrollManagement.DTOS.EmployeeSensitiveInfoDTO;
import com.manthatech.PayrollManagement.DTOS.FullTimeSalaryDTO;
import com.manthatech.PayrollManagement.model.Employee;
import com.manthatech.PayrollManagement.model.EmployeeSensitiveInfo;
import com.manthatech.PayrollManagement.model.EmployeeType;
import com.manthatech.PayrollManagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.createEmployee(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long employeeId, @RequestBody EmployeeDTO employeeDTO) {
        Employee updatedEmployee = employeeService.updateEmployee(employeeId, employeeDTO);
        return ResponseEntity.ok(updatedEmployee);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long employeeId) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employeeDTO);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/employee-types")
    public List<EmployeeType> getAllEmployeeTypes() {
        return Arrays.asList(EmployeeType.values());
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{employeeId}/sensitive-info")
    public ResponseEntity<EmployeeSensitiveInfo> updateEmployeeSensitiveInfo(
            @PathVariable Long employeeId, @RequestBody EmployeeSensitiveInfoDTO sensitiveInfoDTO) {
        EmployeeSensitiveInfo updatedInfo = employeeService.updateEmployeeSensitiveInfo(employeeId, sensitiveInfoDTO);
        return ResponseEntity.ok(updatedInfo);
    }

    public ResponseEntity<EmployeeSensitiveInfoDTO> getEmployeeSensitiveInfo(@PathVariable Long employeeId) {
        return new ResponseEntity<>(employeeService.getEmployeeSensitiveInfo(employeeId), HttpStatus.OK);
    }

//    @GetMapping("/{employeeId}/salary-history")
//    public ResponseEntity<List<FullTimeSalaryDTO>> getEmployeeSalaryHistory(@PathVariable Long employeeId) {
//        List<FullTimeSalaryDTO> salaryHistory = employeeService.getEmployeeSalaryHistory(employeeId);
//        return ResponseEntity.ok(salaryHistory);
//    }
}

