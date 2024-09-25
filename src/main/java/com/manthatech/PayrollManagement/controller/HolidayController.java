package com.manthatech.PayrollManagement.controller;

import com.manthatech.PayrollManagement.DTOS.HolidayRequest;
import com.manthatech.PayrollManagement.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@RestController
@RequestMapping("/api/holidays")
@CrossOrigin
public class HolidayController {
    private final HolidayService holidayService;

    @Autowired
    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<LocalDate>> getHolidaysForMonth(@PathVariable int year, @PathVariable int month) {
        List<LocalDate> holidays = holidayService.getHolidaysForMonth(year, Month.of(month));
        return ResponseEntity.ok(holidays);
    }

    @PostMapping
    public ResponseEntity<Void> addHoliday(@RequestBody HolidayRequest request) {
        holidayService.addHoliday(request.getDate(), request.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
