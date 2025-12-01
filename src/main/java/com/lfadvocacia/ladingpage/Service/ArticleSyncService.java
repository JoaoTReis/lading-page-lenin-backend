package com.lfadvocacia.ladingpage.Service;

import com.lfadvocacia.ladingpage.model.Article;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ArticleSyncService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleSyncService.class);

    @Value("${google.sheets.url}")
    private String sheetUrl;

    private final RestTemplate rest = new RestTemplate();

    public List<Article> sync() {
        try {
            ResponseEntity<ArticleDTO[]> response =
                    rest.getForEntity(sheetUrl, ArticleDTO[].class);

            ArticleDTO[] artigosSheets = response.getBody();
            if (artigosSheets == null) {
                logger.error("A resposta do Google Sheets retornou null");
                return List.of();
            }

            List<Article> artigos = new ArrayList<>();

            for (ArticleDTO dto : artigosSheets) {
                Article a = new Article();
                a.setTitulo(dto.getTitulo());
                a.setLink(dto.getLink());
                a.setThumbnail(dto.getThumbnailURL());
                artigos.add(a);
            }

            return artigos;
        } catch (Exception e) {
            logger.error("Erro ao sincronizar artigos: ", e);
            throw e; // relança para que o Spring registre o 500
        }
    }
}
