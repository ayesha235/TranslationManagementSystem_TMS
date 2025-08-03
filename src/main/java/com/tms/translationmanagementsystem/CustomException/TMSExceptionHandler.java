package com.tms.translationmanagementsystem.CustomException;

import com.tms.translationmanagementsystem.Utils.Response;
import com.tms.translationmanagementsystem.Utils.ResponseCodesEnum;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Hidden
@ControllerAdvice
public class TMSExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TranslationException.class)
    public ResponseEntity<Object> handleTranslationExceptions(TranslationException ex, WebRequest request) {
        ResponseCodesEnum codeEnum;

        // Decide which response code to use based on the actual exception class
        if (ex instanceof SaveTranslationException) {
            codeEnum = ResponseCodesEnum.TRANSLATION_SAVE_FAILED;
        } else if (ex instanceof FetchTranslationException) {
            codeEnum = ResponseCodesEnum.TRANSLATION_NOT_FOUND;
        } else {
            // Default fallback
            codeEnum = ResponseCodesEnum.INTERNAL_SERVER_ERROR;
        }

        // Build response body
        ExceptionResponse responseBody = new ExceptionResponse(codeEnum.code(), codeEnum.message(), ex.getMessage());

        // Wrap in generic response
        Response<ExceptionResponse> response = new Response<>(codeEnum.status(), getClass().getSimpleName(), codeEnum.code(), responseBody);

        return new ResponseEntity<>(response, HttpStatus.valueOf(ResponseCodesEnum.SUCCESS.code()));
    }

    // FIXED: Updated method signature for Spring Boot 3.x
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status,     // Changed from HttpStatus to HttpStatusCode
                                                                  WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String message = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        ExceptionResponse body = new ExceptionResponse(ResponseCodesEnum.BAD_REQUEST.code(), ResponseCodesEnum.BAD_REQUEST.message(), message);

        Response<ExceptionResponse> response = new Response<>(ResponseCodesEnum.BAD_REQUEST.status(), "MethodArgumentNotValidException", ResponseCodesEnum.BAD_REQUEST.code(), body);

        return new ResponseEntity<>(response, HttpStatus.valueOf(ResponseCodesEnum.SUCCESS.code()));

    }

}