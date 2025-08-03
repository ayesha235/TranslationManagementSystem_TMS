package com.tms.translationmanagementsystem.Controller;

import com.tms.translationmanagementsystem.CustomException.FetchTranslationException;
import com.tms.translationmanagementsystem.CustomException.SaveTranslationException;
import com.tms.translationmanagementsystem.Dto.TranslationRequest;
import com.tms.translationmanagementsystem.Dto.TranslationResponse;
import com.tms.translationmanagementsystem.Feature.FetchTranslation.FetchTranslationService;
import com.tms.translationmanagementsystem.Utils.Response;
import com.tms.translationmanagementsystem.Utils.ResponseCodesEnum;
import com.tms.translationmanagementsystem.Feature.SaveTranslation.SaveTranslationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("tms/translation")
@RequiredArgsConstructor
@Validated
public class TranslationController {
    private final FetchTranslationService fetchTranslationService;
    private final SaveTranslationService saveTranslationService;

    @GetMapping("/{id}")
    public ResponseEntity<Response<TranslationResponse>> getTranslation(@PathVariable Long id) throws FetchTranslationException {
        Response<TranslationResponse> response = new Response<>(ResponseCodesEnum.SUCCESS.status(), getClass().getSimpleName(), ResponseCodesEnum.SUCCESS.code(), fetchTranslationService.getById(id));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TranslationResponse>> search(@RequestParam(required = false) String key, @RequestParam(required = false) String content, @RequestParam(required = false) String tag, Pageable pageable) throws FetchTranslationException {
        Page<TranslationResponse> page = fetchTranslationService.search(key, content, tag, pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping("/create")
    public ResponseEntity<Response<TranslationResponse>> saveTranslation(@Valid @RequestBody TranslationRequest request) throws SaveTranslationException {

        TranslationResponse data = saveTranslationService.create(request);

        Response<TranslationResponse> response = new Response<>(ResponseCodesEnum.SUCCESS.status(), getClass().getSimpleName(), ResponseCodesEnum.SUCCESS.code(), data);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<Response<TranslationResponse>> updateTranslation(@Valid @RequestBody TranslationRequest request) throws FetchTranslationException, SaveTranslationException {

        TranslationResponse data = saveTranslationService.update(request);

        Response<TranslationResponse> response = new Response<>(ResponseCodesEnum.SUCCESS.status(), getClass().getSimpleName(), ResponseCodesEnum.SUCCESS.code(), data);

        return ResponseEntity.ok(response);
    }

}
