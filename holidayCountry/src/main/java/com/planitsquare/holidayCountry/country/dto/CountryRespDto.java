package com.planitsquare.holidayCountry.country.dto;

import com.planitsquare.holidayCountry.country.entity.Country;
import com.planitsquare.holidayCountry.external.dto.NagerCountryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CountryRespDto {
    private String countryCode;
    private String countryName;

    public static CountryRespDto from(Country c){
        CountryRespDto r = new CountryRespDto();
        r.countryName = c.getCountryName();
        r.countryCode = c.getCountryCode();
        return r;
    }
}
