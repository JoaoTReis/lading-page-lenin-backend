package com.lfadvocacia.ladingpage.controller;

import com.lfadvocacia.ladingpage.model.Article;
import com.lfadvocacia.ladingpage.utils.ThumbnailUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/artigos")
public class ArticleController {

    private static final String SHEETS_URL =
            "https://script.google.com/macros/s/AKfycbyo7mUeXqB8hNGkpsomPn1hef1Ti0C8Q4SKeewW7sigMHbGzATMkTufSwhYTHpXwvn2/exec";

    @GetMapping
    public List<Article> listar() {

        RestTemplate restTemplate = new RestTemplate();
        Article[] resposta = restTemplate.getForObject(SHEETS_URL, Article[].class);

        return Arrays.stream(resposta)
                .map(art -> {
                    art.setThumbnail(
                            ThumbnailUtils.normalizeThumbnail(art.getThumbnail())
                    );
                    return art;
                })
                .toList();
    }
}
