package com.tms.translationmanagementsystem.CustomException;


import com.tms.translationmanagementsystem.Utils.ResponseCodesEnum;
import lombok.Getter;

@Getter
public class TranslationException extends Exception {
    private final ResponseCodesEnum exceptionCode;
    private final String message;

    public TranslationException(String message) {
        super(message);
        this.message = message;
        this.exceptionCode = null;
    }

    public TranslationException(ResponseCodesEnum exceptionCode, String message) {
        super(message);
        this.message = message;
        this.exceptionCode = exceptionCode;
    }
}

