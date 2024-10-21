package com.toYou.board.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;

import com.toYou.board.domain.Answer;
import com.toYou.board.domain.Board;
import com.toYou.board.domain.Member;

import groovy.transform.ToString;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnswerDto {

	private Integer id;
	private Member answerAuthor;
	private String content;
	private Board board;
	private Set<Member> likedUser;

	private LocalDateTime createdDate;
	private LocalDateTime lastModified;

	// 사용자가 댓글을 DTO를 통해 작성하고 그걸 토대로 찐 엔티티를 생성
	// User-Level =======> DataBase

	// DTO의 @Builder를 호출하는 부분이 해당 도메인의 @Service임.
	// @Repository에서 가져온 찐 엔티티들을 @Service가 @Controller로 넘기기 전에,
	// @Service에 있는 converter 메서드로 @Entity를 DTO로 변환시키는 작업을 하는데
	// 그때 쓰이는 것이 바로 아래 있는 @Builder BoardDTO 임....
	
	public Answer toEntity(Member answerAuthor, String content, Board board) {
		return Answer.builder()
				.answerAuthor(answerAuthor)
				.content(content)
				.board(board)
				.build();
	}

	// 데이터베이스에서 데이터를 가져올때, 엔티티를 전달하는 것이 아닌 그 엔티티의 DTO를 통해 복사본이 전달.
	// User-Level <======== DataBase
	@Builder
	public AnswerDto(Integer id, Member answerAuthor, String content, Board board,
			Set<Member> likedUser, LocalDateTime createdDate, LocalDateTime lastModified) {
		
		this.id = id;
		this.answerAuthor = answerAuthor;
		this.content = content;
		this.board = board;
		this.likedUser = likedUser;
		
		this.createdDate = createdDate;
		this.lastModified = lastModified;
	}
	
	public static Answer convertDto2Entity(AnswerDto answerDto) {
		return Answer.builder()
				.answerAuthor(answerDto.getAnswerAuthor())
				.content(answerDto.getContent())
				.board(answerDto.getBoard())
				.build();
	}

}
