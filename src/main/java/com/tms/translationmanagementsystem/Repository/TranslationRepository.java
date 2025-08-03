package com.tms.translationmanagementsystem.Repository;

import com.tms.translationmanagementsystem.Domain.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long>, JpaSpecificationExecutor<Translation> {
    boolean existsByLocaleAndTranslationKey(String locale, String key);
}
