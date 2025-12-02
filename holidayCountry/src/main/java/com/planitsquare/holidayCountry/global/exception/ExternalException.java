package com.planitsquare.holidayCountry.global.exception;

import lombok.Getter;
@Getter
public class ExternalException extends CustomException{

    public ExternalException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ExternalException(ErrorCode errorCode, Throwable cause) {
        super(cause, errorCode);
    }
}
