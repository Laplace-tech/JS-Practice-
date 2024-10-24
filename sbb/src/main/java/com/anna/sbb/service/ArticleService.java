package com.anna.sbb.service;

import org.springframework.stereotype.Service;

import com.anna.sbb.domain.Article;
import com.anna.sbb.dto.ArticleDto;
import com.anna.sbb.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final 필드 또는 @NonNull이 붙은 필드에 대한 생성자를 자동으로 생성
@Service
public class ArticleService {

	private final ArticleRepository articleRepository;

//	@RequiredArgsConstructor // 생성자를 자동으로 생성
//  *의존성 주입 : 스프링은 ArticleRepository의 @Bean을 찾아서 ArticleService의 생성자에 전달함.
//	public ArticleService(ArticleRepository articleRepository) {
//		this.articleRepository = articleRepository; // 주입된 ArticleRepository를 할당
//	}

	public Article createArticle(ArticleDto articleDto) {
		return this.articleRepository.save(articleDto.toEntity());
	}

}
