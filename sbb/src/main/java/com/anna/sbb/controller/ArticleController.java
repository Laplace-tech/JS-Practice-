package com.anna.sbb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anna.sbb.domain.Article;
import com.anna.sbb.dto.ArticleDto;
import com.anna.sbb.service.ArticleService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/article")
@RequiredArgsConstructor
@RestController
public class ArticleController {

	private final ArticleService articleService;

	@PostMapping("/create")
	public ResponseEntity<Article> createArticle(@RequestBody ArticleDto articleDto) {
		Article savedArticle = this.articleService.createArticle(articleDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
	}

}
