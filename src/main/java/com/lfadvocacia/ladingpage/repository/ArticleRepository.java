package com.lfadvocacia.ladingpage.repository;

import com.lfadvocacia.ladingpage.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
