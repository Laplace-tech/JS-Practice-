package com.anna.sbb.dto;

import com.anna.sbb.domain.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ArticleDto {

	private String title;
	private String content;
	
	public Article toEntity() {
		return Article.builder()
				.title(title)
				.content(content)
				.build();
	}
}
