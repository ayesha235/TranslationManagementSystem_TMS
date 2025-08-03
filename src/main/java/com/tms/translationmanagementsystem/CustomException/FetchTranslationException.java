package com.tms.translationmanagementsystem.CustomException;


import com.tms.translationmanagementsystem.Utils.ResponseCodesEnum;

public class FetchTranslationException extends TranslationException{
    public FetchTranslationException(String message) {
        super(message);
    }

    public FetchTranslationException(ResponseCodesEnum exceptionCode, String message) {
        super(exceptionCode, message);
    }
}
