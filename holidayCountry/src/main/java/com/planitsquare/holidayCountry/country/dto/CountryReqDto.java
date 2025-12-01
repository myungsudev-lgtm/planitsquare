package com.planitsquare.holidayCountry.country.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CountryReqDto {
    private String countryCode;
    private String countryName;
}
