package com.manthatech.PayrollManagement.service;

import com.manthatech.PayrollManagement.DTOS.CountryDTO;
import com.manthatech.PayrollManagement.model.Country;
import com.manthatech.PayrollManagement.repository.CountryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrgService {

    @Autowired
    private CountryRepository countryRepository;

    public Country addCountry(CountryDTO countryDTO) {
        Country newCountry = convertToEntity(countryDTO);
        return countryRepository.save(newCountry);
    }

    public List<CountryDTO> getAllCountries() {
       return countryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CountryDTO getCountryById(Long countryId) {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new EntityNotFoundException("Country Not Found"));
        return convertToDto(country);
    }

    public void deleteCountry(Long countryId) {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new EntityNotFoundException("Country Not Found"));
        countryRepository.delete(country);
    }

    public CountryDTO convertToDto(Country country) {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setId(country.getId());
        countryDTO.setCountry(country.getCountry());
        return countryDTO;
    }

    public Country convertToEntity(CountryDTO countryDTO) {
        Country country = new Country();
        country.setCountry(countryDTO.getCountry());
        return country;
    }
}
