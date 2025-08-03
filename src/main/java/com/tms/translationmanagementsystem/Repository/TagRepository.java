package com.tms.translationmanagementsystem.Repository;

import com.tms.translationmanagementsystem.Domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
     Optional<Tag> findByTagName(String name);
}
