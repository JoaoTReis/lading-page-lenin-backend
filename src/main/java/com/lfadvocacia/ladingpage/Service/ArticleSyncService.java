package com.lfadvocacia.ladingpage.Service;

import com.lfadvocacia.ladingpage.model.Article;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleSyncService {

    @Value("${google.sheets.url}")
    private String sheetUrl;

    private final RestTemplate rest = new RestTemplate();

    public List<Article> sync() {

        ResponseEntity<ArticleDTO[]> response =
                rest.getForEntity(sheetUrl, ArticleDTO[].class);

        ArticleDTO[] artigosSheets = response.getBody();

        List<Article> artigos = new ArrayList<>();

        for (ArticleDTO dto : artigosSheets) {

            Article a = new Article();
            a.setTitulo(dto.getTitulo());
            a.setLink(dto.getLink());
            a.setThumbnail(dto.getThumbnailURL()); // <-- CORRIGIDO

            artigos.add(a);
        }

        return artigos;
    }
}
