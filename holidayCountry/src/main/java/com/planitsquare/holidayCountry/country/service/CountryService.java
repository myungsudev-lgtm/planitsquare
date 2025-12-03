package com.planitsquare.holidayCountry.country.service;

import com.planitsquare.holidayCountry.country.dto.CountryReqDto;
import com.planitsquare.holidayCountry.country.dto.CountryRespDto;
import com.planitsquare.holidayCountry.country.entity.Country;
import com.planitsquare.holidayCountry.country.repository.CountryRepository;
import com.planitsquare.holidayCountry.external.client.NagerClient;
import com.planitsquare.holidayCountry.external.dto.NagerCountryDto;
import com.planitsquare.holidayCountry.global.exception.CustomException;
import com.planitsquare.holidayCountry.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CountryService {
    private final NagerClient nagerClient;
    private final CountryRepository countryRepository;

    @Transactional(readOnly = false)
    public List<CountryRespDto> saveAllFromExternal(){
        List<NagerCountryDto> availableCountries = nagerClient.getAvailableCountries();
        List<Country> countries = availableCountries.stream().map(Country::of).toList();
        List<Country> saved = countryRepository.saveAll(countries);
        return saved.stream().map(CountryRespDto::from).toList();
    }


    public List<CountryRespDto> findAll() {
        List<Country> countries = countryRepository.findAll();
        return countries.stream().map(CountryRespDto::from).toList();
    }

    public CountryRespDto findByCountryCode(String countryCode) {
        Country country = countryRepository.findByCountryCode(countryCode).
                orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        return CountryRespDto.from(country);
    }

    @Transactional(readOnly = false)
    public CountryRespDto update(String countryCode, CountryReqDto reqDto) {
        Country country = countryRepository.findByCountryCode(countryCode).
                orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        country.update(reqDto);
        return CountryRespDto.from(country);
    }
    @Transactional(readOnly = false)
    public CountryRespDto delete(String countryCode) {
        Country country = countryRepository.findByCountryCode(countryCode).
                orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        countryRepository.delete(country);
        return CountryRespDto.from(country);
    }
}
