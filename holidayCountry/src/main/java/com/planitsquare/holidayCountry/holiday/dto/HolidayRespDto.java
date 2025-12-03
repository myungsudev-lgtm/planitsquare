package com.planitsquare.holidayCountry.holiday.dto;

import com.planitsquare.holidayCountry.country.entity.Country;
import com.planitsquare.holidayCountry.holiday.entity.Holiday;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HolidayRespDto {

    private LocalDate date;
    private String localName;
    private String name;
    private String country;
    private Boolean fixed;
    private Boolean global;
    private String counties;
    private Integer launchYear;
    private List<String> types = new ArrayList<>();

    public static HolidayRespDto from(Holiday h){
        HolidayRespDto r = new HolidayRespDto();
        r.date = h.getDate();
        r.localName = h.getLocalName();
        r.name = h.getName();
        r.country = h.getCountry().getCountryCode();
        r.fixed = h.getFixed() != null ? h.getFixed() : false;
        r.global = h.getGlobal() != null ? h.getGlobal() : false;
        r.counties = h.getCounties();
        r.launchYear = h.getLaunchYear();
        r.types = h.getTypes() != null ? h.getTypes() : new ArrayList<>();
        return r;
    }
}
