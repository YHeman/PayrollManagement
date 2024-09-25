package com.manthatech.PayrollManagement.controller;

import com.manthatech.PayrollManagement.DTOS.CountryDTO;
import com.manthatech.PayrollManagement.model.Country;
import com.manthatech.PayrollManagement.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/org")
@CrossOrigin
public class OrgController {

    @Autowired
    private OrgService orgService;

    @PostMapping("/country")
    public ResponseEntity<Country> addCountry(@RequestBody CountryDTO countryDTO) {
        Country addedCountry = orgService.addCountry(countryDTO);
        return new ResponseEntity<>(addedCountry, HttpStatus.CREATED);
    }

    @GetMapping("/country")
    public ResponseEntity<List<CountryDTO>> getAllCountries() {
        List<CountryDTO> countries = orgService.getAllCountries();
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping("/country/{countryId}")
    public ResponseEntity<CountryDTO> getCountryById(@PathVariable Long countryId) {
        CountryDTO country = orgService.getCountryById(countryId);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @DeleteMapping("/country/{countryId}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long countryId) {
        orgService.deleteCountry(countryId);
        return ResponseEntity.noContent().build();
    }
}
