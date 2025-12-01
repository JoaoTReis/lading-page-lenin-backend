package com.lfadvocacia.ladingpage.Service;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleDTO {

    private String titulo;
    private String link;
    private String data;        // formato vindo da planilha: "2025-11-24"
    private String thumbnailURL;
}
