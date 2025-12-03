package com.planitsquare.holidayCountry.holiday.entity;

import com.planitsquare.holidayCountry.country.entity.Country;
import com.planitsquare.holidayCountry.external.dto.NagerHolidayDto;
import com.planitsquare.holidayCountry.holiday.dto.HolidayReqDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "holiday",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_holiday_country_date",
                columnNames = {"country_id", "date", "holiday_name", "counties"}
        )
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private String localName;
    @Column(name = "holiday_name",nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    private Boolean fixed;
    private Boolean global;
    private String counties;
    private Integer launchYear;

    @ElementCollection
    @CollectionTable(
            name = "holiday_type",
            joinColumns = @JoinColumn(name = "holiday_id")
    )
    private List<String> types = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



    public static Holiday of(HolidayReqDto dto, Country country){
        Holiday h = new Holiday();
        h.date = dto.getDate();
        h.localName = dto.getLocalName();
        h.name = dto.getName();
        h.country = country;
        h.fixed = dto.getFixed() != null ? dto.getFixed() : false;
        h.global = dto.getGlobal() != null ? dto.getGlobal() : false;
        h.counties = dto.getCounties();
        h.launchYear = dto.getLaunchYear();
        h.types = dto.getTypes() != null ? dto.getTypes() : new ArrayList<>();
        h.createdAt = LocalDateTime.now();
        return h;
    }

    public static Holiday of(NagerHolidayDto dto, Country country){
        Holiday h = new Holiday();
        h.date = dto.getDate();
        h.localName = dto.getLocalName();
        h.name = dto.getName();
        h.country = country;
        h.fixed = dto.getFixed() != null ? dto.getFixed() : false;
        h.global = dto.getGlobal() != null ? dto.getGlobal() : false;
        h.counties = dto.getCounties() != null ? String.join(",", dto.getCounties()) : null;
        h.launchYear = dto.getLaunchYear();
        h.types = dto.getTypes() != null ? dto.getTypes() : new ArrayList<>();
        h.createdAt = LocalDateTime.now();
        return h;
    }

    public void update(HolidayReqDto dto, Country country){
        this.date = dto.getDate();
        this.localName = dto.getLocalName();
        this.name = dto.getName();
        this.country = country;
        this.fixed = dto.getFixed() != null ? dto.getFixed() : false;
        this.global = dto.getGlobal() != null ? dto.getGlobal() : false;
        this.counties = dto.getCounties();
        this.launchYear = dto.getLaunchYear();
        this.types = dto.getTypes() != null ? dto.getTypes() : new ArrayList<>();
        this.updatedAt = LocalDateTime.now();
    }

}
