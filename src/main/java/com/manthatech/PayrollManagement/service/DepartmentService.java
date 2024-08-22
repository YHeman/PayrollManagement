package com.manthatech.PayrollManagement.service;

import com.manthatech.PayrollManagement.DTOS.DepartmentDTO;
import com.manthatech.PayrollManagement.model.Department;
import com.manthatech.PayrollManagement.model.Employee;
import com.manthatech.PayrollManagement.repository.DepartmentRepository;
import com.manthatech.PayrollManagement.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Department createDepartment(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setDepartmentName(departmentDTO.getDepartmentName());
        department.setLocation(departmentDTO.getLocation());

        if (departmentDTO.getManagerId() != null) {
            Optional<Employee> manager = employeeRepository.findById(departmentDTO.getManagerId());
            manager.ifPresent(department::setManager);
        }

        department.setCreatedAt(LocalDateTime.now());
        department.setUpdatedAt(LocalDateTime.now());
        return departmentRepository.save(department);
    }

    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Department updateDepartment(Long departmentId, DepartmentDTO departmentDTO) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department Not Found"));
        mapDtoToEntity(departmentDTO, department);
        department.setUpdatedAt(LocalDateTime.now());
        return departmentRepository.save(department);
    }

    public void deleteDepartment(Long departmentId) {
        Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
        if (optionalDepartment.isPresent()) {
            departmentRepository.delete(optionalDepartment.get());
        } else {
            throw new EntityNotFoundException("Department not found with id " + departmentId);
        }
    }

    private void mapDtoToEntity(DepartmentDTO departmentDTO, Department department) {
        if (departmentDTO.getDepartmentName() != null) department.setDepartmentName(departmentDTO.getDepartmentName());
        if (departmentDTO.getLocation() != null) department.setLocation(departmentDTO.getLocation());
        if (departmentDTO.getManagerId() != null) {
            Optional<Employee> manager = employeeRepository.findById(departmentDTO.getManagerId());
            manager.ifPresent(department::setManager);
        }

    }

    private DepartmentDTO convertToDTO(Department department) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setDepartmentId(department.getDepartmentId());
        departmentDTO.setDepartmentName(department.getDepartmentName());
        departmentDTO.setLocation(department.getLocation());

        if (department.getManager() != null) {
            departmentDTO.setManagerId(department.getManager().getEmployeeId());
        }

        return departmentDTO;
    }
}