package com.lfadvocacia.ladingpage.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Article {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String titulo;
    @Setter
    @Getter
    private String link;
    @Getter
    @Setter
    private LocalDate data;
    @Getter
    @Setter
    private String thumbnail;
}

