package com.tms.translationmanagementsystem.Feature.FetchTranslation;

import com.tms.translationmanagementsystem.CustomException.FetchTranslationException;
import com.tms.translationmanagementsystem.Dto.TranslationResponse;
import com.tms.translationmanagementsystem.Repository.TranslationRepository;
import com.tms.translationmanagementsystem.Domain.Translation;
import com.tms.translationmanagementsystem.Feature.MapperService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static com.tms.translationmanagementsystem.Feature.FetchTranslation.TranslationSpecifications.*;

@Service
@RequiredArgsConstructor
public class FetchTranslationServiceImpl implements FetchTranslationService {
    private final TranslationRepository translationRepository;
    private final MapperService mapperService;
    private static final Logger logger = LogManager.getLogger(FetchTranslationException.class);

    @Override
    public Page<TranslationResponse> search(String key, String content, String tag, Pageable pageable) throws FetchTranslationException {
        try {
            Specification<Translation> spec = (root, query, cb) -> cb.conjunction();

            if (key != null && !key.isBlank()) {
                spec = spec.and(hasKeyLike(key));
            }

            if (content != null && !content.isBlank()) {
                spec = spec.and(hasContentLike(content));
            }

            if (tag != null && !tag.isBlank()) {
                spec = spec.and(hasTagName(tag));
            }

            return translationRepository.findAll(spec, pageable).map(mapperService::domainToDto);
        } catch (Exception ex) {
            logger.error("Fetch Translation Exception: {}", ex.getMessage(), ex);
            throw new FetchTranslationException("Error fetching pageable translations");
        }
    }

    @Override
    public TranslationResponse getById(Long id) throws FetchTranslationException {
        try {
            Translation translation = translationRepository.findById(id).orElseThrow(() -> new FetchTranslationException("Translation not found with id: " + id));
            return mapperService.domainToDto(translation);
        } catch (Exception ex) {
            logger.error("Fetch Translation Exception: {}", ex.getMessage(), ex);
            throw new FetchTranslationException("Error fetching translation");
        }
    }
}
