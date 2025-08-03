package com.tms.translationmanagementsystem.Feature;

import com.tms.translationmanagementsystem.Domain.Tag;
import com.tms.translationmanagementsystem.Domain.Translation;
import com.tms.translationmanagementsystem.Repository.TagRepository;
import com.tms.translationmanagementsystem.Repository.TranslationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class DataInitializerFactory implements CommandLineRunner {

    private final TranslationRepository translationRepository;
    private final TagRepository tagRepository;

    @Override
    public void run(String... args) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String timestamp = LocalDateTime.now().format(formatter);

        // Create 50 tags first
        List<Tag> tags = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            Tag tag = new Tag();
            tag.setTagName("test_tag_" + i + timestamp);
            tags.add(tag);
        }
        tagRepository.saveAll(tags);

        // Bulk insert translations in batches
        int total = 200;
        int batchSize = 200;
        List<Translation> batch = new ArrayList<>();

        Random random = new Random();

        for (int i = 1; i <= total; i++) {
            Translation t = new Translation();
            t.setTranslationKey("test_key" + i + timestamp);
            t.setLocale("en");
            t.setContent("This is sample content #" + i);

            HashSet<Tag> tagSet = new HashSet<>();

            // Randomly assign 1–3 tags
            Collections.shuffle(tags);
            List<Tag> newTag = tags.subList(0, random.nextInt(3) + 1);
            for (var tg : newTag) {
                var existingTag = tagRepository.findByTagName(tg.getTagName());
                if (existingTag.isPresent()) {
                    tagSet.add(existingTag.get());
                } else {
                    tagSet.add(tg);
                }
            }
            t.setTags(tagSet);

            batch.add(t);

            if (batch.size() == batchSize) {
                translationRepository.saveAll(batch);
                batch.clear();
                System.out.println("Inserted: " + i + " records");
            }
        }

        // insert remaining
        if (!batch.isEmpty()) {
            translationRepository.saveAll(batch);
        }

        System.out.println("Finished inserting 200 translations.");
    }
}
