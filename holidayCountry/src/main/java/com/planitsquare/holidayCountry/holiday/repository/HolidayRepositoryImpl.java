package com.planitsquare.holidayCountry.holiday.repository;

import com.planitsquare.holidayCountry.country.entity.QCountry;
import com.planitsquare.holidayCountry.holiday.entity.Holiday;
import com.planitsquare.holidayCountry.holiday.entity.QHoliday;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class HolidayRepositoryImpl implements HolidayRepositoryCustom{
    private final JPAQueryFactory q;
    QHoliday holiday = QHoliday.holiday;
    QCountry country = QCountry.country;

    @Override
    public Page<Holiday> search(Integer year, String countryCode, String type, LocalDate from, LocalDate to, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        if(year != null) builder.and(holiday.date.year().eq(year));
        if(countryCode != null && !countryCode.isBlank()) builder.and(holiday.country.countryCode.eq(countryCode));
        if(type != null && !type.isBlank()) builder.and(holiday.types.contains(type));
        if(from != null) builder.and(holiday.date.goe(from));
        if(to != null) builder.and(holiday.date.loe(to));

        List<Holiday> holidays = q.selectFrom(holiday)
                .leftJoin(holiday.country, country)
                .fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(holiday.date.asc())
                .fetch();

        Long count = q.select(holiday.count())
                .from(holiday)
                .leftJoin(holiday.country, country)
                .where(builder)
                .fetchOne();

        return PageableExecutionUtils.getPage(holidays, pageable, ()->count);
    }


}
