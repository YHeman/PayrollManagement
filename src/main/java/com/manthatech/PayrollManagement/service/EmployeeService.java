package com.manthatech.PayrollManagement.service;

import com.manthatech.PayrollManagement.DTOS.EmployeeDTO;
import com.manthatech.PayrollManagement.DTOS.EmployeeSensitiveInfoDTO;
import com.manthatech.PayrollManagement.model.*;
import com.manthatech.PayrollManagement.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeSensitiveInfoRepository sensitiveInfoRepository;

    @Autowired
    private CountryRepository countryRepository;

    public Employee createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        mapDtoToEntity(employeeDTO, employee);
        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdatedAt(LocalDateTime.now());
        return employeeRepository.save(employee);
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public Employee updateEmployee(Long employeeId, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        mapDtoToEntity(employeeDTO, employee);
        employee.setUpdatedAt(LocalDateTime.now());
        return employeeRepository.save(employee);
    }

    public EmployeeDTO getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        return mapEntityToDto(employee);
    }

    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        employeeRepository.delete(employee);
    }

    public EmployeeSensitiveInfo updateEmployeeSensitiveInfo(Long employeeId, EmployeeSensitiveInfoDTO sensitiveInfoDTO) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        EmployeeSensitiveInfo sensitiveInfo = employee.getSensitiveInfo();
        if (sensitiveInfo == null) {
            sensitiveInfo = new EmployeeSensitiveInfo();
            sensitiveInfo.setEmployee(employee);
        }
        mapSensitiveInfoDtoToEntity(sensitiveInfoDTO, sensitiveInfo);
        sensitiveInfo.setUpdatedAt(LocalDateTime.now());
        return sensitiveInfoRepository.save(sensitiveInfo);
    }
    public EmployeeSensitiveInfoDTO getEmployeeSensitiveInfo(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        EmployeeSensitiveInfo employeeSensitiveInfo = employee.getSensitiveInfo();
        return mapEntityToSensitiveInfoDTO(employeeSensitiveInfo);
    }

    private void mapDtoToEntity(EmployeeDTO employeeDTO, Employee employee) {
        if (employeeDTO.getFirstName() != null) employee.setFirstName(employeeDTO.getFirstName());
        if (employeeDTO.getLastName() != null) employee.setLastName(employeeDTO.getLastName());
        if (employeeDTO.getEmail() != null) employee.setEmail(employeeDTO.getEmail());
        if (employeeDTO.getPhone() != null) employee.setPhone(employeeDTO.getPhone());
        if (employeeDTO.getHireDate() != null) employee.setHireDate(employeeDTO.getHireDate());
        if (employeeDTO.getStatus() != null) employee.setStatus(employeeDTO.getStatus());

        if (employeeDTO.getCountryId() != null) {
            Country country = countryRepository.findById(employeeDTO.getCountryId())
                    .orElseThrow(() -> new EntityNotFoundException("Country Not Found"));
            employee.setCountry(country);
        }

        if (employeeDTO.getJobId() != null) {
            Job job = jobRepository.findById(employeeDTO.getJobId())
                    .orElseThrow(() -> new EntityNotFoundException("Job not found"));
            employee.setJob(job);
        }

        if (employeeDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                    .orElseThrow(() -> new EntityNotFoundException("Department not found"));
            employee.setDepartment(department);
        }
    }

    private EmployeeDTO mapEntityToDto(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(employee.getEmployeeId());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setPhone(employee.getPhone());
        employeeDTO.setHireDate(employee.getHireDate());
        employeeDTO.setStatus(employee.getStatus());
        employeeDTO.setJobId(employee.getJob().getJobId());
        employeeDTO.setDepartmentId(employee.getDepartment().getDepartmentId());
//        employeeDTO.setCountryId(employee.getCountry().getId());
        return employeeDTO;
    }

    private void mapSensitiveInfoDtoToEntity(EmployeeSensitiveInfoDTO sensitiveInfoDTO, EmployeeSensitiveInfo sensitiveInfo) {
        sensitiveInfo.setPan(sensitiveInfoDTO.getPan());
        sensitiveInfo.setBankAccountNumber(sensitiveInfoDTO.getBankAccountNumber());
        sensitiveInfo.setBankName(sensitiveInfoDTO.getBankName());
        sensitiveInfo.setIfscCode(sensitiveInfoDTO.getIfscCode());
        sensitiveInfo.setAadhaarNumber(sensitiveInfoDTO.getAadhaarNumber());
    }

    private EmployeeSensitiveInfoDTO mapEntityToSensitiveInfoDTO(EmployeeSensitiveInfo employeeSensitiveInfo) {
        EmployeeSensitiveInfoDTO employeeSensitiveInfoDTO = new EmployeeSensitiveInfoDTO();
        employeeSensitiveInfoDTO.setPan(employeeSensitiveInfo.getPan());
        employeeSensitiveInfoDTO.setAadhaarNumber(employeeSensitiveInfo.getAadhaarNumber());
        employeeSensitiveInfoDTO.setBankName(employeeSensitiveInfo.getBankName());
        employeeSensitiveInfoDTO.setBankAccountNumber(employeeSensitiveInfo.getBankAccountNumber());
        employeeSensitiveInfoDTO.setIfscCode(employeeSensitiveInfo.getIfscCode());
        return employeeSensitiveInfoDTO;
    }

//    public List<FullTimeSalaryDTO> getEmployeeSalaryHistory(Long employeeId) {
//        Employee employee = employeeRepository.findById(employeeId)
//                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
//        return baseSalaryService.getSalariesByEmployee(employee).stream()
//                .map(SalaryConverter::convertToDto)
//                .collect(Collectors.toList());
//    }

}

