package com.manthatech.PayrollManagement.controller;

import com.manthatech.PayrollManagement.DTOS.EmployeeRequestDTO;
import com.manthatech.PayrollManagement.DTOS.EmployeeResponseDTO;
import com.manthatech.PayrollManagement.DTOS.EmployeeSensitiveInfoDTO;
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
@CrossOrigin
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = employeeService.createEmployee(employeeRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long employeeId, @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        Employee updatedEmployee = employeeService.updateEmployee(employeeId, employeeRequestDTO);
        return ResponseEntity.ok(updatedEmployee);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long employeeId) {
        EmployeeResponseDTO employeeResponseDTO = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employeeResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();
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

    @PostMapping("/{employeeId}/sensitive-info")
    public ResponseEntity<EmployeeSensitiveInfo> addEmployeeSensitiveInfo(@PathVariable Long employeeId, @RequestBody EmployeeSensitiveInfoDTO sensitiveInfoDTO) {
        return ResponseEntity.ok(employeeService.addEmployeeSensitiveInfo(employeeId, sensitiveInfoDTO));
    }

    @PutMapping("/{employeeId}/sensitive-info")
    public ResponseEntity<EmployeeSensitiveInfo> updateEmployeeSensitiveInfo(
            @PathVariable Long employeeId, @RequestBody EmployeeSensitiveInfoDTO sensitiveInfoDTO) {
        EmployeeSensitiveInfo updatedInfo = employeeService.updateEmployeeSensitiveInfo(employeeId, sensitiveInfoDTO);
        return ResponseEntity.ok(updatedInfo);
    }

    @GetMapping("/{employeeId}/sensitive-info")
    public ResponseEntity<EmployeeSensitiveInfoDTO> getEmployeeSensitiveInfo(@PathVariable Long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        if(employee.getSensitiveInfo() != null) return new ResponseEntity<>(employeeService.getEmployeeSensitiveInfo(employeeId), HttpStatus.OK);
        else return ResponseEntity.notFound().build();
    }

//    @GetMapping("/{employeeId}/salary-history")
//    public ResponseEntity<List<FullTimeSalaryDTO>> getEmployeeSalaryHistory(@PathVariable Long employeeId) {
//        List<FullTimeSalaryDTO> salaryHistory = employeeService.getEmployeeSalaryHistory(employeeId);
//        return ResponseEntity.ok(salaryHistory);
//    }
}

