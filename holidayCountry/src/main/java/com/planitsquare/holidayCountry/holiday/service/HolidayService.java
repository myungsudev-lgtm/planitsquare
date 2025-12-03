package com.planitsquare.holidayCountry.holiday.service;

import com.planitsquare.holidayCountry.country.entity.Country;
import com.planitsquare.holidayCountry.country.repository.CountryRepository;
import com.planitsquare.holidayCountry.external.client.NagerClient;
import com.planitsquare.holidayCountry.external.dto.NagerHolidayDto;
import com.planitsquare.holidayCountry.global.exception.CustomException;
import com.planitsquare.holidayCountry.global.exception.ErrorCode;
import com.planitsquare.holidayCountry.holiday.dto.HolidayRespDto;
import com.planitsquare.holidayCountry.holiday.entity.Holiday;
import com.planitsquare.holidayCountry.holiday.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HolidayService {
    private final NagerClient nagerClient;
    private final HolidayRepository holidayRepository;
    private final CountryRepository countryRepository;

    @Transactional(readOnly = false)
    public List<HolidayRespDto> saveAllFromExternal(int year, String countryCode){
        List<NagerHolidayDto> publicHolidays = nagerClient.getPublicHolidays(year, countryCode);

        Country country = countryRepository.findByCountryCode(countryCode)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));

        Set<String> existKeys = new HashSet<>(
                holidayRepository.findAllKeysByCountryAndYear(countryCode,year)
        );

        List<Holiday> holidayList = publicHolidays.stream()
                .map(dto -> Holiday.of(dto, country))
                .filter(h -> !existKeys.contains(makeKey(h)))
                .toList();

        if (holidayList.isEmpty()) {
            return List.of();
        }

        List<Holiday> holidays = holidayRepository.saveAll(holidayList);
        return holidays.stream().map(HolidayRespDto::from).toList();
    }


    public Page<HolidayRespDto> search(Integer year, String country, String type, LocalDate from, LocalDate to, Pageable pageable) {

        Page<Holiday> holidays = holidayRepository.search(year,country, type, from, to, pageable);
        return holidays.map(HolidayRespDto::from);
    }

    @Transactional(readOnly = false)
    public List<HolidayRespDto> refresh(Integer year, String countryCode) {
        //국가 확인
        Country country = countryRepository.findByCountryCode(countryCode)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        //외부 api 호출, 동기화..
        List<NagerHolidayDto> newPublicHolidays = nagerClient.getPublicHolidays(year, countryCode);
        holidayRepository.deleteByCountryAndYear(countryCode, year);

        List<Holiday> holidayList = newPublicHolidays.stream().map(dto -> Holiday.of(dto, country)).toList();
        List<Holiday> holidays = holidayRepository.saveAll(holidayList);
        return holidays.stream().map(HolidayRespDto::from).toList();

    }

    @Transactional(readOnly = false)
    public void delete(Integer year, String countryCode) {

        boolean exists = countryRepository.existsByCountryCode(countryCode);
        if(!exists) throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);

        holidayRepository.deleteByCountryAndYear(countryCode, year);

    }

    private String makeKey(Holiday h){
        return h.getCountry().getCountryCode() + "|" + h.getDate() + "|" + h.getLocalName();
    }
}
