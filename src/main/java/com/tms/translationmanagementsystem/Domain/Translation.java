package com.tms.translationmanagementsystem.Domain;

import com.tms.translationmanagementsystem.Utils.Constants;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "TRANSLATION", schema = Constants.SCHEMA_NAME)
public class Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String translationKey;
    private String locale;
    private String content;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TRANSLATION_TAG_MAPPING", joinColumns = @JoinColumn(name = "TRANSLATION_ID"), inverseJoinColumns = @JoinColumn(name = "TAG_ID"))
    private Set<Tag> tags;
}
