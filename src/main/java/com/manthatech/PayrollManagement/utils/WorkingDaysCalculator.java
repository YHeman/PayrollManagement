package com.manthatech.PayrollManagement.utils;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Component
public class WorkingDaysCalculator {

    public int getWorkingDaysInMonth(int year, Month month, List<LocalDate> holidays) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        int workingDays = 0;

        while (!start.isAfter(end)) {
            if (!isWeekend(start) && !holidays.contains(start)) {
                workingDays++;
            }
            start = start.plusDays(1);
        }
        return workingDays;
    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}

