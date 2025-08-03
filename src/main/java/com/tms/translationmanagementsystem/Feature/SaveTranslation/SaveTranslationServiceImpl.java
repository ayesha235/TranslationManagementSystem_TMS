package com.tms.translationmanagementsystem.Feature.SaveTranslation;

import com.tms.translationmanagementsystem.CustomException.FetchTranslationException;
import com.tms.translationmanagementsystem.CustomException.SaveTranslationException;
import com.tms.translationmanagementsystem.Domain.Tag;
import com.tms.translationmanagementsystem.Domain.Translation;
import com.tms.translationmanagementsystem.Dto.TranslationRequest;
import com.tms.translationmanagementsystem.Dto.TranslationResponse;
import com.tms.translationmanagementsystem.Feature.MapperService;
import com.tms.translationmanagementsystem.Repository.TagRepository;
import com.tms.translationmanagementsystem.Repository.TranslationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class SaveTranslationServiceImpl implements SaveTranslationService {
    private final TranslationRepository translationRepository;
    private final TagRepository tagRepository;
    private final MapperService mapperService;
    private static final Logger logger = LogManager.getLogger(SaveTranslationServiceImpl.class);


    @Override
    public TranslationResponse create(TranslationRequest request) throws SaveTranslationException {
        try {
            // Prevent duplicate locale-key pair
            if (isValidLocaleAndKey(request.getLocale(), request.getTranslationKey())) {
                throw new SaveTranslationException("Cannot add duplicate locale and key.");
            }

            // Map incoming DTO to domain
            Translation translation = mapperService.dtoToDomain(request);

            // Handle tags
            handleTagsInTranslation(request.getTags(), translation);

            // Save and return response
            Translation saved = translationRepository.save(translation);
            return mapperService.domainToDto(saved);

        } catch (SaveTranslationException ex) {
            throw ex;

        } catch (Exception ex) {
            logger.error("Save Translation Exception: {}", ex.getMessage(), ex);
            throw new SaveTranslationException("Unexpected error occurred while saving translation.");
        }
    }


    @Override
    public TranslationResponse update(TranslationRequest request) throws SaveTranslationException, FetchTranslationException {
        try {
            // Validate input
            Long id = request.getId();
            if (id == null) {
                throw new FetchTranslationException("Translation ID is required for update.");
            }

            // Fetch existing translation or throw
            Translation existing = translationRepository.findById(id).orElseThrow(() -> new FetchTranslationException("Translation not found with ID: " + id));

            // Check if locale/key is being changed to something that already exists
            boolean isLocaleChanged = !existing.getLocale().equalsIgnoreCase(request.getLocale());
            boolean isKeyChanged = !existing.getTranslationKey().equalsIgnoreCase(request.getTranslationKey());

            if ((isLocaleChanged || isKeyChanged) && isValidLocaleAndKey(request.getLocale(), request.getTranslationKey())) {
                throw new SaveTranslationException("Cannot update to duplicate locale and key.");
            }

            Translation updatedTranslation = mapperService.dtoToDomain(request);
            updatedTranslation.setId(existing.getId());

            // Handle tags
            handleTagsInTranslation(request.getTags(), updatedTranslation);

            // Update the entity and return the response
            Translation saved = translationRepository.save(updatedTranslation);
            return mapperService.domainToDto(saved);

        } catch (FetchTranslationException | SaveTranslationException ex) {
            // Let custom exceptions bubble up to be handled by ControllerAdvice
            throw ex;

        } catch (Exception ex) {
            logger.error("Unexpected error during updateTranslation: {}", ex.getMessage(), ex);
            throw new SaveTranslationException("Unexpected error occurred during translation update.");
        }
    }


    private boolean isValidLocaleAndKey(String locale, String key) {
        return translationRepository.existsByLocaleAndTranslationKey(locale, key);
    }

    private void handleTagsInTranslation(Set<String> IncomingTags, Translation translation) {
        if (IncomingTags != null && !IncomingTags.isEmpty()) {
            Set<Tag> tags = IncomingTags.stream().map(tagName -> tagRepository.findByTagName(tagName).orElseGet(() -> tagRepository.save(mapperService.mapTagRequest(tagName)))).collect(Collectors.toSet());
            translation.setTags(tags);
        }
    }
}

