package com.planitsquare.holidayCountry.country.entity;

import com.planitsquare.holidayCountry.country.dto.CountryReqDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "country")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "country_code", nullable = false, unique = true)
    private String countryCode;
    @Column(name = "country_name", nullable = false)
    private String countryName;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Country of(CountryReqDto dto){
        Country c = new Country();
        c.countryName = dto.getCountryName();
        c.countryCode = dto.getCountryCode();
        c.createdAt = LocalDateTime.now();
        return c;
    }

    public void update(CountryReqDto dto) {
        this.countryName = dto.getCountryName();
        this.countryCode = dto.getCountryCode();
        this.updatedAt = LocalDateTime.now();
    }
}
