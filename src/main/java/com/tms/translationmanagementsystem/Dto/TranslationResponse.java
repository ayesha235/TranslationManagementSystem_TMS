package com.tms.translationmanagementsystem.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class TranslationResponse {
    private Long id;
    private String translationKey;
    private String locale;
    private String content;
    private String createdBy;
    private LocalDate createdAt;
    private String updatedBy;
    private LocalDate updatedAt;
    private Set<TagResponse> tags;

}
