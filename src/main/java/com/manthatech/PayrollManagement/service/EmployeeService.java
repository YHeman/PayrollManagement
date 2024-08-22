package com.manthatech.PayrollManagement.service;

import com.manthatech.PayrollManagement.DTOS.EmployeeDTO;
import com.manthatech.PayrollManagement.DTOS.EmployeeSensitiveInfoDTO;
import com.manthatech.PayrollManagement.DTOS.FullTimeSalaryDTO;
import com.manthatech.PayrollManagement.model.Department;
import com.manthatech.PayrollManagement.model.Employee;
import com.manthatech.PayrollManagement.model.EmployeeSensitiveInfo;
import com.manthatech.PayrollManagement.model.Job;
import com.manthatech.PayrollManagement.repository.DepartmentRepository;
import com.manthatech.PayrollManagement.repository.EmployeeRepository;
import com.manthatech.PayrollManagement.repository.EmployeeSensitiveInfoRepository;
import com.manthatech.PayrollManagement.repository.JobRepository;
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
    private BaseSalaryService baseSalaryService;

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

//    public List<FullTimeSalaryDTO> getEmployeeSalaryHistory(Long employeeId) {
//        Employee employee = employeeRepository.findById(employeeId)
//                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
//        return baseSalaryService.getSalariesByEmployee(employee).stream()
//                .map(SalaryConverter::convertToDto)
//                .collect(Collectors.toList());
//    }

    private void mapDtoToEntity(EmployeeDTO employeeDTO, Employee employee) {
        if (employeeDTO.getFirstName() != null) employee.setFirstName(employeeDTO.getFirstName());
        if (employeeDTO.getLastName() != null) employee.setLastName(employeeDTO.getLastName());
        if (employeeDTO.getEmail() != null) employee.setEmail(employeeDTO.getEmail());
        if (employeeDTO.getPhone() != null) employee.setPhone(employeeDTO.getPhone());
        if (employeeDTO.getHireDate() != null) employee.setHireDate(employeeDTO.getHireDate());
        if (employeeDTO.getStatus() != null) employee.setStatus(employeeDTO.getStatus());

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
        return employeeDTO;
    }

    private void mapSensitiveInfoDtoToEntity(EmployeeSensitiveInfoDTO sensitiveInfoDTO, EmployeeSensitiveInfo sensitiveInfo) {
        sensitiveInfo.setPan(sensitiveInfoDTO.getPan());
        sensitiveInfo.setBankAccountNumber(sensitiveInfoDTO.getBankAccountNumber());
        sensitiveInfo.setBankName(sensitiveInfoDTO.getBankName());
        sensitiveInfo.setIfscCode(sensitiveInfoDTO.getIfscCode());
        sensitiveInfo.setAadhaarNumber(sensitiveInfoDTO.getAadhaarNumber());
    }
}

