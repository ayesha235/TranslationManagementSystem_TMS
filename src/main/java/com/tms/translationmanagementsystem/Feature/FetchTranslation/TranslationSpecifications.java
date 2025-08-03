package com.tms.translationmanagementsystem.Feature.FetchTranslation;

import com.tms.translationmanagementsystem.Domain.Tag;
import com.tms.translationmanagementsystem.Domain.Translation;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class TranslationSpecifications {

    public static Specification<Translation> hasKeyLike(String key) {
        return (root, query, cb) -> {
            if (key == null || key.isBlank()) return null;
            return cb.like(cb.lower(root.get("translationKey")), "%" + key.toLowerCase() + "%");
        };
    }

    public static Specification<Translation> hasContentLike(String content) {
        return (root, query, cb) -> {
            if (content == null || content.isBlank()) return null;
            return cb.like(cb.lower(root.get("content")), "%" + content.toLowerCase() + "%");
        };
    }

    public static Specification<Translation> hasTagName(String tagName) {
        return (root, query, cb) -> {
            if (tagName == null || tagName.isBlank()) return null;
            Join<Translation, Tag> tags = root.join("tags", JoinType.LEFT);
            return cb.equal(cb.lower(tags.get("tagName")), tagName.toLowerCase());
        };
    }
}
