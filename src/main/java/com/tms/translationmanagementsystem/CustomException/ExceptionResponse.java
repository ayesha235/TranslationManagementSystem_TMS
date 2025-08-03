package com.tms.translationmanagementsystem.CustomException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse {
    private int errorCode;
    private String message;
    private String details;

    public ExceptionResponse(int errorCode, String message, String details) {
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
    }
}
