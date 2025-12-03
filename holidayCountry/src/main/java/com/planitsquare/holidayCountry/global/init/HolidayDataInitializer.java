package com.planitsquare.holidayCountry.global.init;

import com.planitsquare.holidayCountry.country.dto.CountryRespDto;
import com.planitsquare.holidayCountry.country.service.CountryService;
import com.planitsquare.holidayCountry.holiday.service.HolidayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class HolidayDataInitializer {
    private final HolidayService holidayService;
    private final CountryService countryService;

    @Async
    @EventListener(ApplicationReadyEvent.class)
    public void initHolidayData(){
        log.info("[Initializer] 공휴일 초기 데이터 적재 시작");

        List<CountryRespDto> countries = countryService.saveAllFromExternal();
        log.info("[Initializer] 총 국가 개수 = {}", countries.size());

        for (CountryRespDto country : countries) {
            String countryCode = country.getCountryCode();
            for (int year = 2020; year <= 2025; year++) {
                try {
                    log.info("[Initializer] {} / {} 공휴일 동기화 시작", countryCode, year);
                    holidayService.saveAllFromExternal(year, countryCode);
                    log.info("[Initializer] {} / {} 공휴일 동기화 완료", countryCode, year);
                } catch (Exception e) {
                    // 오류메세지 전달
                    log.error("[Initializer] {} / {} 공휴일 동기화 실패: {}", countryCode, year, e.getMessage(), e);
                }
            }
        }

        log.info("[Initializer] 공휴일 초기 데이터 적재 완료");
    }

}


