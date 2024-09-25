package com.manthatech.PayrollManagement.service;

import com.manthatech.PayrollManagement.DTOS.CountryDTO;
import com.manthatech.PayrollManagement.DTOS.SalaryStructureDTO;
import com.manthatech.PayrollManagement.DTOS.StructureAllowanceDTO;
import com.manthatech.PayrollManagement.DTOS.StructureDeductionDTO;
import com.manthatech.PayrollManagement.model.*;
import com.manthatech.PayrollManagement.repository.AllowanceRepository;
import com.manthatech.PayrollManagement.repository.CountryRepository;
import com.manthatech.PayrollManagement.repository.DeductionRepository;
import com.manthatech.PayrollManagement.repository.SalaryStructureRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SalaryStructureService {

    @Autowired
    private SalaryStructureRepository salaryStructureRepository;

    @Autowired
    private AllowanceRepository allowanceRepository;

    @Autowired
    private DeductionRepository deductionRepository;

    @Autowired
    private CountryRepository countryRepository;

    public SalaryStructureDTO createSalaryStructure(SalaryStructureDTO salaryStructureDTO) {
        SalaryStructure salaryStructure = new SalaryStructure();
        salaryStructure.setName(salaryStructureDTO.getName());
        salaryStructure.setDescription(salaryStructureDTO.getDescription());
        salaryStructure.setBaseSalary(salaryStructureDTO.getBaseSalary());

        Set<StructureAllowance> structureAllowances = salaryStructureDTO.getStructureAllowances().stream()
                .map(dto -> {
                    StructureAllowance structureAllowance = new StructureAllowance();
                    Allowance allowance = allowanceRepository.findById(dto.getAllowanceId())
                            .orElseThrow(() -> new EntityNotFoundException("Allowance not found"));
                    structureAllowance.setAllowance(allowance);
                    structureAllowance.setAmount(dto.getAmount());
                    structureAllowance.setSalaryStructure(salaryStructure);
                    return structureAllowance;
                }).collect(Collectors.toSet());

        salaryStructure.setStructureAllowances(structureAllowances);

        Set<StructureDeduction> structureDeductions = salaryStructureDTO.getStructureDeductions().stream()
                .map(dto -> {
                    StructureDeduction structureDeduction = new StructureDeduction();
                    Deduction deduction = deductionRepository.findById(dto.getDeductionId())
                            .orElseThrow(() -> new EntityNotFoundException("Deduction not found"));
                    structureDeduction.setDeduction(deduction);
                    structureDeduction.setAmount(dto.getAmount());
                    structureDeduction.setSalaryStructure(salaryStructure);
                    return structureDeduction;
                }).collect(Collectors.toSet());

        salaryStructure.setStructureDeductions(structureDeductions);

        if(salaryStructureDTO.getCountryId() != null) salaryStructure.setCountry(countryRepository.findById(salaryStructureDTO.getCountryId())
                .orElseThrow(() -> new EntityNotFoundException("Country Not Found")));


        SalaryStructure savedStructure = salaryStructureRepository.save(salaryStructure);

        return convertToDTO(savedStructure);
    }

    public SalaryStructureDTO updateSalaryStructure(Long id, SalaryStructureDTO salaryStructureDTO) {
        SalaryStructure salaryStructure = salaryStructureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Salary Structure not found"));

        salaryStructure.setName(salaryStructureDTO.getName());
        salaryStructure.setDescription(salaryStructureDTO.getDescription());

        salaryStructure.setBaseSalary(salaryStructureDTO.getBaseSalary());

        salaryStructure.getStructureAllowances().clear();
        Set<StructureAllowance> updatedStructureAllowances = salaryStructureDTO.getStructureAllowances().stream()
                .map(dto -> {
                    StructureAllowance structureAllowance = new StructureAllowance();
                    Allowance allowance = allowanceRepository.findById(dto.getAllowanceId())
                            .orElseThrow(() -> new EntityNotFoundException("Allowance not found"));
                    structureAllowance.setAllowance(allowance);
                    structureAllowance.setAmount(dto.getAmount());
                    structureAllowance.setSalaryStructure(salaryStructure);
                    return structureAllowance;
                }).collect(Collectors.toSet());

        salaryStructure.getStructureAllowances().addAll(updatedStructureAllowances);

        salaryStructure.getStructureDeductions().clear();
        Set<StructureDeduction> updatedStructureDeductions = salaryStructureDTO.getStructureDeductions().stream()
                .map(dto -> {
                    StructureDeduction structureDeduction = new StructureDeduction();
                    Deduction deduction = deductionRepository.findById(dto.getDeductionId())
                            .orElseThrow(() -> new EntityNotFoundException("Deduction not found"));
                    structureDeduction.setDeduction(deduction);
                    structureDeduction.setAmount(dto.getAmount());
                    structureDeduction.setSalaryStructure(salaryStructure);
                    return structureDeduction;
                }).collect(Collectors.toSet());

        salaryStructure.getStructureDeductions().addAll(updatedStructureDeductions);

        if(salaryStructureDTO.getCountryId() != null) salaryStructure.setCountry(countryRepository.findById(salaryStructureDTO.getCountryId())
                .orElseThrow(() -> new EntityNotFoundException("Country Not Found")));

        SalaryStructure updatedSalaryStructure = salaryStructureRepository.save(salaryStructure);

        return convertToDTO(updatedSalaryStructure);
    }

    public List<SalaryStructureDTO> getAllStructures() {
        return salaryStructureRepository.findAll().stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public SalaryStructureDTO getSalaryStructure(Long id) {
        SalaryStructure salaryStructure = salaryStructureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Salary Structure not found"));
        return convertToDTO(salaryStructure);
    }

    public void deleteSalaryStructure(Long id) {
        SalaryStructure salaryStructure = salaryStructureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Salary Structure not found"));
        salaryStructureRepository.delete(salaryStructure);
    }

    private SalaryStructureDTO convertToDTO(SalaryStructure salaryStructure) {
        SalaryStructureDTO dto = new SalaryStructureDTO();
        dto.setId(salaryStructure.getId());
        dto.setName(salaryStructure.getName());
        dto.setDescription(salaryStructure.getDescription());
        dto.setBaseSalary(salaryStructure.getBaseSalary());

        Set<StructureAllowanceDTO> structureAllowanceDTOs = salaryStructure.getStructureAllowances().stream()
                .map(allowance -> {
                    StructureAllowanceDTO structureAllowanceDTO = new StructureAllowanceDTO();
                    structureAllowanceDTO.setId(allowance.getId());
                    structureAllowanceDTO.setAllowanceId(allowance.getAllowance().getId());
                    structureAllowanceDTO.setAmount(allowance.getAmount());
                    return structureAllowanceDTO;
                }).collect(Collectors.toSet());

        dto.setStructureAllowances(structureAllowanceDTOs);

        Set<StructureDeductionDTO> structureDeductionDTOs = salaryStructure.getStructureDeductions().stream()
                .map(deduction -> {
                    StructureDeductionDTO structureDeductionDTO = new StructureDeductionDTO();
                    structureDeductionDTO.setId(deduction.getId());
                    structureDeductionDTO.setDeductionId(deduction.getDeduction().getId());
                    structureDeductionDTO.setAmount(deduction.getAmount());
                    return structureDeductionDTO;
                }).collect(Collectors.toSet());

        dto.setStructureDeductions(structureDeductionDTOs);
        if(salaryStructure.getCountry() != null) {
            Country country = countryRepository.findById(salaryStructure.getCountry().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Country Not Found"));
            dto.setCountryId(country.getId());
            dto.setCountryName(country.getCountry());
        }

        return dto;
    }

}

