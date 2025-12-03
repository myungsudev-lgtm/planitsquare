package com.planitsquare.holidayCountry.holiday.repository;

import com.planitsquare.holidayCountry.holiday.entity.Holiday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface HolidayRepositoryCustom {
    Page<Holiday> search(Integer year, String country, String type, LocalDate fromDate, LocalDate toDate, Pageable pageable);

}
