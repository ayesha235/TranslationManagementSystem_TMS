package com.tms.translationmanagementsystem.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TagRequest {
    private Long id;
    @NotNull(message = "Tag cannot be null")
    @NotEmpty(message = "Tag cannot be empty")
    @Size(max = 100, message = "Tag name should not exceed 100 characters.")
    private String tagName;

}
