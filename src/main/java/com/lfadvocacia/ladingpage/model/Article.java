package com.lfadvocacia.ladingpage.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Article {

    private String titulo;
    private String link;
    private String thumbnail;  // corresponde a ThumbnailURL
}
