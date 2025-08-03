package com.tms.translationmanagementsystem.Utils;

public enum ResponseCodesEnum {

    // 2xx - Success
    SUCCESS(200, "SUCCESS", "Operation completed successfully"),
    CREATED(201, "CREATED", "Resource created successfully"),

    // 4xx - Client errors
    BAD_REQUEST(400, "BAD_REQUEST", "The request is invalid or malformed"),
    UNAUTHORIZED(401, "UNAUTHORIZED", "Authentication is required or has failed"),

    // 5xx - Server errors
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "An unexpected error occurred on the server"),

    // Custom App Codes
    TRANSLATION_SAVE_FAILED(1002, "TRANSLATION_SAVE_FAILED", "Failed to save translation data"),
    TRANSLATION_NOT_FOUND(1003, "TRANSLATION_NOT_FOUND", "Requested translation does not exist");

    private final int code;
    private final String status;
    private final String message;

    ResponseCodesEnum(int code, String status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String status() {
        return status;
    }

    public String message() {
        return message;
    }
}
