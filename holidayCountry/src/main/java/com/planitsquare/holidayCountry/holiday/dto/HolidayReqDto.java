package com.planitsquare.holidayCountry.holiday.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HolidayReqDto {
    private LocalDate date;
    private String localName;
    private String name;
    private String countryCode;;
    private Boolean fixed;
    private Boolean global;
    private String counties;
    private Integer launchYear;
    private List<String> types;
}
