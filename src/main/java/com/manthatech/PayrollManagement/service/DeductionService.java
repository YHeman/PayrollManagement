package com.manthatech.PayrollManagement.service;

import com.manthatech.PayrollManagement.DTOS.DeductionDTO;
import com.manthatech.PayrollManagement.model.Deduction;
import com.manthatech.PayrollManagement.repository.DeductionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DeductionService {

    @Autowired
    private DeductionRepository deductionRepository;

    public Deduction createDeduction(DeductionDTO deductionDTO) {
        Deduction deduction = new Deduction();
        mapDtoToEntity(deductionDTO, deduction);
        return deductionRepository.save(deduction);
    }

    public List<DeductionDTO> getAllDeductions() {
        return deductionRepository.findAll().stream()
                .map(this::mapEntityTODto)
                .collect(Collectors.toList());
    }

    public DeductionDTO getDeductionById(Long deductionId) {
        Deduction deduction = deductionRepository.findById(deductionId)
                .orElseThrow(() -> new EntityNotFoundException("Deduction Not Found"));
        return mapEntityTODto(deduction);
    }

    public Deduction updateDeduction(Long deductionId, DeductionDTO deductionDTO) {
        Deduction deduction = deductionRepository.findById(deductionId)
                .orElseThrow(() -> new EntityNotFoundException("Deduction not found"));
        mapDtoToEntity(deductionDTO, deduction);
        return deductionRepository.save(deduction);
    }

    public void deleteDeduction(Long deductionId) {
        Deduction deduction = deductionRepository.findById(deductionId)
                .orElseThrow(() -> new EntityNotFoundException("Deduction not found"));
        deductionRepository.delete(deduction);
    }

    private void mapDtoToEntity(DeductionDTO deductionDTO, Deduction deduction) {
        deduction.setName(deductionDTO.getName());
        deduction.setDescription(deductionDTO.getDescription());
        deduction.setStatutory(deductionDTO.isStatutory());
        deduction.setMandatory(deductionDTO.isMandatory());
    }

    private DeductionDTO mapEntityTODto(Deduction deduction) {
        DeductionDTO deductionDTO = new DeductionDTO();
        deductionDTO.setId(deduction.getId());
        deductionDTO.setName(deduction.getName());
        deductionDTO.setDescription(deduction.getDescription());
        deductionDTO.setStatutory(deduction.isStatutory());
        deductionDTO.setMandatory(deduction.isMandatory());
        return deductionDTO;
    }
}