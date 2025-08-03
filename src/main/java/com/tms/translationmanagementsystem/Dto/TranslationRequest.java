package com.tms.translationmanagementsystem.Dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TranslationRequest {
    private Long id;
    @NotNull(message = "Key cannot be null")
    @NotEmpty(message = "Key cannot be empty")
    @Size(max = 100, message = "Key should not exceed 100 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9_.-]+$", message = "Key must only contain letters, numbers, dots, hyphens, or underscores")
    private String translationKey;
    @NotNull(message = "Locale cannot be null")
    @NotEmpty(message = "Locale cannot be empty")
    @Size(max = 100, message = "Locale should not exceed 100 characters.")
    @Pattern(regexp = "^[a-z]{2}(-[A-Z]{2})?$", message = "Locale must be in the format 'en' or 'en-US'")
    private String locale;
    @NotNull(message = "Content cannot be null")
    @NotEmpty(message = "Content cannot be empty")
    @Size(max = 200, message = "Content should not exceed 1=200 characters.")
    private String content;
    @Valid
    @NotNull(message = "Tags cannot be null")
    @NotEmpty(message = "Tags cannot be empty")
    private Set<String> tags;

}
