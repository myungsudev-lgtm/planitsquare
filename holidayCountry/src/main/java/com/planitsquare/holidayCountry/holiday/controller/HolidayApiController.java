package com.planitsquare.holidayCountry.holiday.controller;

import com.planitsquare.holidayCountry.holiday.dto.HolidayRespDto;
import com.planitsquare.holidayCountry.holiday.service.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/holiday")
public class HolidayApiController {
    private final HolidayService holidayService;
    //모든 국가 + 공휴일 + 모든 기간/ 페이징 처리
    @GetMapping
    public Page<HolidayRespDto> findAllHolidays(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String type,
            @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @PageableDefault(size = 50, sort = "date") Pageable pageable
    ){
       return holidayService.search(year, country, type, from, to, pageable);
    }


    @PutMapping("refresh")
    public ResponseEntity<?> updateHolidaysByCountryCode(
            @RequestParam Integer year,
            @RequestParam String country
    ){
        List<HolidayRespDto> result = holidayService.refresh(year, country);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteHolidayByCountryCode(
            @RequestParam Integer year,
            @RequestParam String country
    ){
        holidayService.delete(year,country);
        return ResponseEntity.noContent().build();
    }
}
