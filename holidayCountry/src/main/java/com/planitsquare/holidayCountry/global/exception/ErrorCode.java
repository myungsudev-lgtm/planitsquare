package com.planitsquare.holidayCountry.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // ===== 내부 error =====
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),                  // 400
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다."),        // 404
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다."), // 405
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."), // 500

    // ===== 외부 API error =====
    EXTERNAL_BAD_REQUEST(HttpStatus.BAD_REQUEST, "외부 API 잘못된 요청입니다."),          // 400
    EXTERNAL_NOT_FOUND(HttpStatus.NOT_FOUND, "외부 API 리소스를 찾을 수 없습니다."),      // 404
    EXTERNAL_METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "외부 API 메서드가 허용되지 않습니다."), // 405
    EXTERNAL_SERVER_ERROR(HttpStatus.BAD_GATEWAY, "외부 API 서버 오류가 발생했습니다."),  // 502 (BAD_GATEWAY)
    EXTERNAL_INVALID_RESPONSE(HttpStatus.BAD_GATEWAY, "외부 API 응답 형식 오류"), // 502
    EXTERNAL_UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, "외부 API 처리 중 알 수 없는 오류가 발생했습니다."); // 500

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
