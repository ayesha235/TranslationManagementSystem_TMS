package com.tms.translationmanagementsystem.Domain;

import jakarta.persistence.*;
import lombok.Data;

import static com.tms.translationmanagementsystem.Utils.Constants.SCHEMA_NAME;


@Entity
@Data
@Table(name = "TAG", schema = SCHEMA_NAME)
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tagName;
}
