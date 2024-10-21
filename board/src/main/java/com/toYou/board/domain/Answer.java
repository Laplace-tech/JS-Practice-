package com.toYou.board.domain;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.toYou.board.dto.AnswerDto;

import jakarta.persistence.*;
import lombok.*;

@EntityListeners(value = { AuditingEntityListener.class })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Answer {

	// ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// CONTENT
	@Lob
	@Column(nullable = false)
	private String content;

	// ANSWER_AUTHOR_ID
	@ManyToOne
	@JoinColumn(name = "ANSWER_AUTHOR_ID")
	private Member answerAuthor;

	// BOARD_AUTHOR_ID
	@ManyToOne
	@JoinColumn(name = "BOARD_ID")
	private Board board;

	@ManyToMany(cascade = CascadeType.REMOVE)
	@JoinTable(
			name = "answer_likes", 
			joinColumns = @JoinColumn(name = "ANSWER_ID"),
			inverseJoinColumns = @JoinColumn(name = "MEMBER_ID")
	)
	private Set<Member> likedUser = new HashSet<>();

	// CREATED_DATE
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdDate;

	// LAST_MODIFIED
	@Column
	private LocalDateTime lastModified;

	@Builder
	public Answer(String content, Member answerAuthor, Board board) {
		this.content = content;
		this.answerAuthor = answerAuthor;
		this.board = board;
	}
	
	public static AnswerDto convertEntity2Dto(Answer answerEntity) {
		return AnswerDto.builder()
				.id(answerEntity.getId())
				.content(answerEntity.getContent())
				.answerAuthor(answerEntity.getAnswerAuthor())
				.board(answerEntity.getBoard())
				.likedUser(answerEntity.getLikedUser())
				.createdDate(answerEntity.getCreatedDate())
				.lastModified(answerEntity.getLastModified())
				.build();
	}
	
}
