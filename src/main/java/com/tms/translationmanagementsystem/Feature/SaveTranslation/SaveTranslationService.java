package com.tms.translationmanagementsystem.Feature.SaveTranslation;

import com.tms.translationmanagementsystem.CustomException.FetchTranslationException;
import com.tms.translationmanagementsystem.CustomException.SaveTranslationException;
import com.tms.translationmanagementsystem.Dto.TranslationRequest;
import com.tms.translationmanagementsystem.Dto.TranslationResponse;

public interface SaveTranslationService {
    TranslationResponse create(TranslationRequest request) throws SaveTranslationException;

    TranslationResponse update(TranslationRequest request) throws FetchTranslationException, SaveTranslationException;
}
