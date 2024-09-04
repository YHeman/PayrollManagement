package com.manthatech.PayrollManagement.service;

import com.manthatech.PayrollManagement.model.Holiday;
import com.manthatech.PayrollManagement.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HolidayService {
    private final HolidayRepository holidayRepository;

    @Autowired
    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    public List<LocalDate> getHolidaysForMonth(int year, Month month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        return holidayRepository.findByDateBetween(startDate, endDate)
                .stream()
                .map(Holiday::getDate)
                .collect(Collectors.toList());
    }

    public void addHoliday(LocalDate date, String description) {
        Holiday holiday = new Holiday();
        holiday.setDate(date);
        holiday.setDescription(description);
        holidayRepository.save(holiday);
    }
}
