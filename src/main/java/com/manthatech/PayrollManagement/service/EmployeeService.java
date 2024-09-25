package com.manthatech.PayrollManagement.service;

import com.manthatech.PayrollManagement.DTOS.*;
import com.manthatech.PayrollManagement.model.*;
import com.manthatech.PayrollManagement.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    private FullTimeSalaryRepository fullTimeSalaryRepository;

    public Employee createEmployee(EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = new Employee();
        mapDtoToEntity(employeeRequestDTO, employee);
        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdatedAt(LocalDateTime.now());
        return employeeRepository.save(employee);
    }

    public List<EmployeeResponseDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public Employee updateEmployee(Long employeeId, EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        mapDtoToEntity(employeeRequestDTO, employee);
        employee.setUpdatedAt(LocalDateTime.now());
        return employeeRepository.save(employee);
    }

    public EmployeeResponseDTO getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        return mapEntityToDto(employee);
    }

    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        employeeRepository.delete(employee);
    }

    public EmployeeSensitiveInfo addEmployeeSensitiveInfo(Long employeeId, EmployeeSensitiveInfoDTO sensitiveInfoDTO) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        EmployeeSensitiveInfo sensitiveInfo = new EmployeeSensitiveInfo();
        sensitiveInfo.setEmployee(employee);
        mapSensitiveInfoDtoToEntity(sensitiveInfoDTO, sensitiveInfo);
        sensitiveInfo.setUpdatedAt(LocalDateTime.now());
        return sensitiveInfoRepository.save(sensitiveInfo);
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

    public Employee getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee Not Found"));
    }
    public EmployeeSensitiveInfoDTO getEmployeeSensitiveInfo(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        EmployeeSensitiveInfo employeeSensitiveInfo = employee.getSensitiveInfo();
        return mapEntityToSensitiveInfoDTO(employeeSensitiveInfo);
    }

    private void mapDtoToEntity(EmployeeRequestDTO employeeRequestDTO, Employee employee) {
        if (employeeRequestDTO.getFirstName() != null) employee.setFirstName(employeeRequestDTO.getFirstName());
        if (employeeRequestDTO.getLastName() != null) employee.setLastName(employeeRequestDTO.getLastName());
        if (employeeRequestDTO.getEmail() != null) employee.setEmail(employeeRequestDTO.getEmail());
        if (employeeRequestDTO.getPhone() != null) employee.setPhone(employeeRequestDTO.getPhone());
        if (employeeRequestDTO.getHireDate() != null) employee.setHireDate(employeeRequestDTO.getHireDate());
        if (employeeRequestDTO.isEnabled()) employee.setEnabled(employeeRequestDTO.isEnabled());
        if(employeeRequestDTO.getEmployeeType() != null) employee.setEmployeeType(employeeRequestDTO.getEmployeeType());
        if(employeeRequestDTO.getCurrentSalaryId() != null) {
            Salary currentSalary = fullTimeSalaryRepository.findById(employeeRequestDTO.getCurrentSalaryId())
                            .orElseThrow(() -> new EntityNotFoundException("Salary Not Found"));
            employee.setCurrentSalary(currentSalary);
        }
        else {
            Optional<Salary> latestSalary = employee.getSalaryHistory().stream()
                    .max(Comparator.comparing(Salary::getPaymentDate));
            latestSalary.ifPresent(employee::setCurrentSalary);
        }

        if (employeeRequestDTO.getCountryId() != null) {
            Country country = countryRepository.findById(employeeRequestDTO.getCountryId())
                    .orElseThrow(() -> new EntityNotFoundException("Country Not Found"));
            employee.setCountry(country);
        }

        if (employeeRequestDTO.getJobId() != null) {
            Job job = jobRepository.findById(employeeRequestDTO.getJobId())
                    .orElseThrow(() -> new EntityNotFoundException("Job not found"));
            employee.setJob(job);
        }

        if (employeeRequestDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(employeeRequestDTO.getDepartmentId())
                    .orElseThrow(() -> new EntityNotFoundException("Department not found"));
            employee.setDepartment(department);
        }

    }

    private EmployeeResponseDTO mapEntityToDto(Employee employee) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEnabled(employee.isEnabled());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());
        dto.setHireDate(employee.getHireDate());
        if(employee.getCurrentSalary() != null) dto.setCurrentSalaryId(employee.getCurrentSalary().getId());
        dto.setEmployeeType(employee.getEmployeeType());
        dto.setDepartment(convertToDTO(employee.getDepartment()));
        dto.setJob(convertToDTO(employee.getJob()));
        if (employee.getCountry() != null) dto.setCountry(convertToDTO(employee.getCountry()));
        return dto;
    }


    private void mapSensitiveInfoDtoToEntity(EmployeeSensitiveInfoDTO sensitiveInfoDTO, EmployeeSensitiveInfo sensitiveInfo) {
        sensitiveInfo.setPan(sensitiveInfoDTO.getPan());
        sensitiveInfo.setBankAccountNumber(sensitiveInfoDTO.getBankAccountNumber());
        sensitiveInfo.setBankName(sensitiveInfoDTO.getBankName());
        sensitiveInfo.setIfscCode(sensitiveInfoDTO.getIfscCode());
        sensitiveInfo.setAadhaarNumber(sensitiveInfoDTO.getAadhaarNumber());
        sensitiveInfo.setUan(sensitiveInfoDTO.getUan());
    }

    private EmployeeSensitiveInfoDTO mapEntityToSensitiveInfoDTO(EmployeeSensitiveInfo employeeSensitiveInfo) {
        EmployeeSensitiveInfoDTO employeeSensitiveInfoDTO = new EmployeeSensitiveInfoDTO();
        employeeSensitiveInfoDTO.setPan(employeeSensitiveInfo.getPan());
        employeeSensitiveInfoDTO.setAadhaarNumber(employeeSensitiveInfo.getAadhaarNumber());
        employeeSensitiveInfoDTO.setBankName(employeeSensitiveInfo.getBankName());
        employeeSensitiveInfoDTO.setBankAccountNumber(employeeSensitiveInfo.getBankAccountNumber());
        employeeSensitiveInfoDTO.setIfscCode(employeeSensitiveInfo.getIfscCode());
        employeeSensitiveInfoDTO.setUan(employeeSensitiveInfo.getUan());
        return employeeSensitiveInfoDTO;
    }

    private DepartmentDTO convertToDTO(Department department) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setDepartmentId(department.getDepartmentId());
        departmentDTO.setDepartmentName(department.getDepartmentName());
        departmentDTO.setLocation(department.getLocation());
        if (department.getManager() != null) departmentDTO.setManagerId(department.getManager().getEmployeeId());
        return departmentDTO;
    }

    public JobDTO convertToDTO(Job job) {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setJobId(job.getJobId());
        jobDTO.setJobTitle(job.getJobTitle());
        jobDTO.setDescription(job.getDescription());
        return jobDTO;
    }

    public CountryDTO convertToDTO(Country country) {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setId(country.getId());
        countryDTO.setCountryName(country.getCountry());
        return countryDTO;
    }

//    public List<FullTimeSalaryDTO> getEmployeeSalaryHistory(Long employeeId) {
//        Employee employee = employeeRepository.findById(employeeId)
//                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
//        return baseSalaryService.getSalariesByEmployee(employee).stream()
//                .map(SalaryConverter::convertToDto)
//                .collect(Collectors.toList());
//    }

}

