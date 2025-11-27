package com.lfadvocacia.ladingpage.Service;

import com.lfadvocacia.ladingpage.model.Article;
import com.lfadvocacia.ladingpage.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleSyncService {

    @Value("${google.sheets.url}")
    private String sheetUrl;

    @Autowired
    private ArticleRepository repo;

    private final RestTemplate rest = new RestTemplate();

    public List<Article> sync() {

        ResponseEntity<ArticleDTO[]> response =
                rest.getForEntity(sheetUrl, ArticleDTO[].class);

        ArticleDTO[] artigosSheets = response.getBody();
        repo.deleteAll(); // limpa tabela antes de sincronizar

        List<Article> saved = new ArrayList<>();

        for (ArticleDTO dto : artigosSheets) {
            Article a = new Article();
            a.setTitulo(dto.getTitulo());
            a.setLink(dto.getLink());
            a.setThumbnail(dto.getTag()); // tag = thumbnail hoje

            // tratar datas vindo da planilha
            if (dto.getData() != null && !dto.getData().isBlank()) {
                // data vem assim: 2025-11-24T03:00:00.000Z
                LocalDate d = LocalDate.parse(dto.getData().substring(0, 10));
                a.setData(d);
            } else {
                a.setData(null);
            }

            saved.add(repo.save(a));
        }

        return saved;
    }
}
