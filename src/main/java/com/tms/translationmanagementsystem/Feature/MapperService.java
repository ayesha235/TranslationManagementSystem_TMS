package com.tms.translationmanagementsystem.Feature;

import com.tms.translationmanagementsystem.Domain.Tag;
import com.tms.translationmanagementsystem.Domain.Translation;
import com.tms.translationmanagementsystem.Dto.TagResponse;
import com.tms.translationmanagementsystem.Dto.TranslationRequest;
import com.tms.translationmanagementsystem.Dto.TranslationResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MapperService {

    public TranslationResponse domainToDto(Translation domain) {
        if (domain == null) {
            return null;
        }
        TranslationResponse dto = new TranslationResponse();
        dto.setId(domain.getId());
        dto.setContent(domain.getContent());
        dto.setLocale(domain.getLocale());
        dto.setTranslationKey(domain.getTranslationKey());

        if (domain.getTags() != null && !domain.getTags().isEmpty()) {
            dto.setTags(domain.getTags().stream().map(tag -> {
                TagResponse tagResponse = new TagResponse();
                tagResponse.setId(tag.getId());
                tagResponse.setTagName(tag.getTagName());
                return tagResponse;
            }).collect(Collectors.toSet()));
        } else {
            dto.setTags(null);
        }
        return dto;
    }

    public Translation dtoToDomain(TranslationRequest dto) {
        if (dto == null) {
            return null;
        }
        Translation domain = new Translation();
        domain.setContent(dto.getContent());
        domain.setLocale(dto.getLocale());
        domain.setTranslationKey(dto.getTranslationKey());
        return domain;
    }

    public Tag mapTagRequest(String tag) {
        if (tag == null) {
            return null;
        }
        Tag domain = new Tag();
        domain.setTagName(tag);
        return domain;
    }

    public TagResponse domainToDto(Tag domain) {
        if (domain == null) {
            return null;
        }
        TagResponse dto = new TagResponse();
        dto.setId(domain.getId());
        dto.setTagName(domain.getTagName());
        return dto;
    }
}
