package com.planitsquare.holidayCountry.country.controller;

import com.planitsquare.holidayCountry.country.dto.CountryReqDto;
import com.planitsquare.holidayCountry.country.dto.CountryRespDto;
import com.planitsquare.holidayCountry.country.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/country")
@RequiredArgsConstructor
public class CountryApiController {
    private final CountryService countryService;

    @GetMapping
    public List<CountryRespDto> findAllCountries(){
        return countryService.findAll();
    }

    @GetMapping("{countryCode}")
    public CountryRespDto findCountryByCountryCode(@PathVariable String countryCode){
        return countryService.findByCountryCode(countryCode);
    }


}
