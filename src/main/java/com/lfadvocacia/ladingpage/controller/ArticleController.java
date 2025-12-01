package com.lfadvocacia.ladingpage.controller;

import com.lfadvocacia.ladingpage.Service.ArticleSyncService;
import com.lfadvocacia.ladingpage.model.Article;
import com.lfadvocacia.ladingpage.utils.ThumbnailUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artigos")
public class ArticleController {

    private final ArticleSyncService syncService;

    public ArticleController(ArticleSyncService syncService) {
        this.syncService = syncService;
    }

    @GetMapping
    public List<Article> listar() {

        return syncService.sync()
                .stream()
                .map(art -> {
                    art.setThumbnail(
                            ThumbnailUtils.normalizeThumbnail(art.getThumbnail())
                    );
                    return art;
                })
                .toList();
    }
}
