package com.anna.sbb.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false, unique = true)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@Lob
	@Column(nullable = false)
	private String content;
	
	@Builder
	public Article(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
}
