package com.tms.translationmanagementsystem.CustomException;


import com.tms.translationmanagementsystem.Utils.ResponseCodesEnum;

public class SaveTranslationException extends TranslationException{
    public SaveTranslationException(String message) {
        super(message);
    }

    public SaveTranslationException(ResponseCodesEnum exceptionCode, String message) {
        super(exceptionCode, message);
    }
}

