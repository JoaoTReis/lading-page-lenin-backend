package com.lfadvocacia.ladingpage.Service;

import com.lfadvocacia.ladingpage.model.Article;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleSyncService {

    @Value("${google.sheets.url}")
    private String sheetUrl;

    private final RestTemplate rest;

    public ArticleSyncService() {
        this.rest = new RestTemplate();

        // Configura o RestTemplate para aceitar JSON mesmo quando o content-type é text/html
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        List<MediaType> mediaTypes = new ArrayList<>(converter.getSupportedMediaTypes());
        mediaTypes.add(MediaType.TEXT_HTML); // adiciona text/html
        converter.setSupportedMediaTypes(mediaTypes);

        converters.add(converter);
        rest.setMessageConverters(converters);
    }

    public List<Article> sync() {

        // Faz a requisição para o Apps Script
        ResponseEntity<ArticleDTO[]> response =
                rest.getForEntity(sheetUrl, ArticleDTO[].class);

        ArticleDTO[] artigosSheets = response.getBody();

        List<Article> artigos = new ArrayList<>();

        // Mapeia para o modelo Article
        if (artigosSheets != null) {
            for (ArticleDTO dto : artigosSheets) {
                Article a = new Article();
                a.setTitulo(dto.getTitulo());
                a.setLink(dto.getLink());
                a.setThumbnail(dto.getThumbnailURL()); // usa o thumbnailURL do Apps Script
                artigos.add(a);
            }
        }

        return artigos;
    }
}
