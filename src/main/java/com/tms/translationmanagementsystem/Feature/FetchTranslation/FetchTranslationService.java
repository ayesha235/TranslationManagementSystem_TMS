package com.tms.translationmanagementsystem.Feature.FetchTranslation;

import com.tms.translationmanagementsystem.Dto.TranslationResponse;
import com.tms.translationmanagementsystem.CustomException.FetchTranslationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FetchTranslationService {
    Page<TranslationResponse> search(String key, String content, String tag, Pageable pageable) throws FetchTranslationException;
    TranslationResponse getById(Long id) throws FetchTranslationException;


}
