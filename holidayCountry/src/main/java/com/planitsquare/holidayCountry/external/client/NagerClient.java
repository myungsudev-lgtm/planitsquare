package com.planitsquare.holidayCountry.external.client;

import com.planitsquare.holidayCountry.external.dto.NagerCountryDto;
import com.planitsquare.holidayCountry.external.dto.NagerHolidayDto;
import com.planitsquare.holidayCountry.global.exception.ErrorCode;
import com.planitsquare.holidayCountry.global.exception.ExternalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NagerClient {
    private final RestTemplate restTemplate;
    private static final String COUNTRY_URL = "https://date.nager.at/api/v3/AvailableCountries";
    private static final String HOLIDAY_URL = "https://date.nager.at/api/v3/PublicHolidays/";

    public List<NagerCountryDto> getAvailableCountries(){
        log.info("[NagerClient] getAvailableCountries url={}", COUNTRY_URL);
        try{
            NagerCountryDto[] nagerCountryDtos = restTemplate.getForObject(COUNTRY_URL, NagerCountryDto[].class);
            if (nagerCountryDtos == null) {
                log.warn("[NagerClient] getAvailableCountries is null");
                return Collections.emptyList();
            }
            List<NagerCountryDto> result = Arrays.stream(nagerCountryDtos).toList();
            log.debug("[NagerClient] getAvailableCountries size ={}",result.size());
            return result;
        } catch (HttpMessageNotReadableException e) {
            throw new ExternalException(ErrorCode.EXTERNAL_INVALID_RESPONSE, e); // json직렬화 실패...
        } catch (HttpClientErrorException e) {
            throw new ExternalException(ErrorCode.EXTERNAL_BAD_REQUEST, e); // 클라 잘못된 요청..
        } catch (HttpServerErrorException e) {
            throw new ExternalException(ErrorCode.EXTERNAL_SERVER_ERROR, e);
        } catch (Exception e) {
            throw new ExternalException(ErrorCode.EXTERNAL_UNKNOWN, e);
        }
    }

    public List<NagerHolidayDto> getPublicHolidays(int year , String countryCode){
        String url = HOLIDAY_URL + year + "/" + countryCode;
        log.info("[NagerClient] getPublicHoliday url = {}",url );
        try {
            NagerHolidayDto[] nagerHolidayDtos = restTemplate
                    .getForObject(url, NagerHolidayDto[].class);
            if (nagerHolidayDtos == null) {
                log.warn("[NagerClient] getPublicHoliday is null");
                return Collections.emptyList();
            }
            List<NagerHolidayDto> result = Arrays.stream(nagerHolidayDtos).toList();
            log.debug("[NagerClient] getPublicHoliday size = {}", result.size());

            return result;

        } catch (HttpMessageNotReadableException e) {
            throw new ExternalException(ErrorCode.EXTERNAL_INVALID_RESPONSE, e);
        } catch (HttpClientErrorException e) {
            throw new ExternalException(ErrorCode.EXTERNAL_BAD_REQUEST, e); // 클라 잘못된 요청..
        } catch (HttpServerErrorException e) {
            throw new ExternalException(ErrorCode.EXTERNAL_SERVER_ERROR, e);
        } catch (Exception e) {
            throw new ExternalException(ErrorCode.EXTERNAL_UNKNOWN, e);
        }
    }

}
