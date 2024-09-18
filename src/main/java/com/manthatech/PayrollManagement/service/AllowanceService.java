package com.manthatech.PayrollManagement.service;

import com.manthatech.PayrollManagement.DTOS.AllowanceDTO;
import com.manthatech.PayrollManagement.model.Allowance;
import com.manthatech.PayrollManagement.model.Country;
import com.manthatech.PayrollManagement.repository.AllowanceRepository;
import com.manthatech.PayrollManagement.repository.CountryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AllowanceService {

    @Autowired
    private AllowanceRepository allowanceRepository;

    @Autowired
    private CountryRepository countryRepository;

    public List<AllowanceDTO> getAllAllowances() {
        return allowanceRepository.findAll().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public Allowance createAllowance(AllowanceDTO allowanceDTO) {
        Allowance allowance = new Allowance();
        mapDtoToEntity(allowanceDTO, allowance);
        return allowanceRepository.save(allowance);
    }

    public AllowanceDTO getAllowanceById(Long allowanceId) {
        Allowance allowance = allowanceRepository.findById(allowanceId)
                .orElseThrow(() -> new EntityNotFoundException("Allowance Not Found"));
        return mapEntityToDto(allowance);
    }

    public Allowance updateAllowance(Long allowanceId, AllowanceDTO allowanceDTO) {
        Allowance allowance = allowanceRepository.findById(allowanceId)
                .orElseThrow(() -> new EntityNotFoundException("Allowance not found"));
        mapDtoToEntity(allowanceDTO, allowance);
        return allowanceRepository.save(allowance);
    }

    public void deleteAllowance(Long allowanceId) {
        Allowance allowance = allowanceRepository.findById(allowanceId)
                .orElseThrow(() -> new EntityNotFoundException("Allowance not found"));
        allowanceRepository.delete(allowance);
    }

    private void mapDtoToEntity(AllowanceDTO allowanceDTO, Allowance allowance) {
        allowance.setName(allowanceDTO.getName());
        allowance.setDescription(allowanceDTO.getDescription());
        allowance.setTaxable(allowanceDTO.isTaxable());
        allowance.setMandatory(allowanceDTO.isMandatory());
        if(allowanceDTO.getCountry_id() != null) {
            Country country = countryRepository.findById(allowanceDTO.getCountry_id())
                    .orElseThrow(() -> new EntityNotFoundException("Country Not Found"));
            allowance.setCountry(country);
        }

    }

    private AllowanceDTO mapEntityToDto(Allowance allowance) {
        AllowanceDTO allowanceDTO = new AllowanceDTO();
        allowanceDTO.setId(allowance.getId());
        allowanceDTO.setName(allowance.getName());
        allowanceDTO.setDescription(allowance.getDescription());
        allowanceDTO.setTaxable(allowance.isTaxable());
        allowanceDTO.setMandatory(allowance.isMandatory());
        if (allowance.getCountry() != null) allowanceDTO.setCountry_id(allowance.getCountry().getId());
        return allowanceDTO;
    }
}