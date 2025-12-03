package com.planitsquare.holidayCountry.holiday.repository;

import com.planitsquare.holidayCountry.holiday.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface HolidayRepository extends JpaRepository<Holiday,Long>, HolidayRepositoryCustom {

    @Query("""
        select concat(h.country.countryCode, '|', h.date, '|', h.localName)
        from Holiday h
        where h.country.countryCode = :countryCode
          and year(h.date) = :year
    """)
    List<String> findAllKeysByCountryAndYear(String countryCode, int year);

    @Query("""
        select h
        from Holiday h
        where h.country.countryCode = :countryCode
          and (:year is null or year(h.date) = :year)
    """)
    List<Holiday> findByCountryAndYear(String countryCode, Integer year);

}
