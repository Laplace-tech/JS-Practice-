package com.toYou.board.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.toYou.board.domain.Answer;
import com.toYou.board.domain.Board;
import com.toYou.board.domain.Member;

import groovy.transform.ToString;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardDto {

	private Integer id;
	private Member boardAuthor;
	private String subject;
	private String content;
	private LocalDateTime createdDate;
	private LocalDateTime lastModified; // 이 새끼 문제가 많음.
	private Integer viewCount;

	private List<Answer> answerList;
	private Set<Member> likedUsers;

	
	// 데이터베이스에서 데이터를 가져올때, 엔티티를 전달하는 것이 아닌 그 엔티티의 DTO를 통해 복사본이 전달.
	// User-Level <======== DataBase
	
	// DTO의 @Builder를 호출하는 부분이 해당 도메인의 @Service임. 
	// @Repository에서 가져온 찐 엔티티들을 @Service가 @Controller로 넘기기 전에, 
	// @Service에 있는 converter 메서드로 @Entity를 DTO로 변환시키는 작업을 하는데 
	// 그때 쓰이는 것이 바로 아래 있는 @Builder BoardDTO 임....
	@Builder
	public BoardDto(Integer id, Member boardAuthor, String subject, String content, 
			LocalDateTime createdDate, LocalDateTime lastModified, Integer viewCount,
			List<Answer> answerList, Set<Member> likedUsers) {
		
		this.id = id;
		this.boardAuthor = boardAuthor;
		this.subject = subject;
		this.content = content;
		this.createdDate = createdDate;
		this.lastModified = lastModified;
		this.viewCount = viewCount;
		
		this.answerList = answerList;
		this.likedUsers = likedUsers;
	}
	
	
	// 잘되는지 테스트 하려면? 
	// 		ㄴ> 질문 등록하기 -> 질문 게시 테스트 필요
	
	// User-Level =======> DataBase
	public static Board convertDto2Entity(BoardDto boardDto) {
		
		return Board.builder()
				.boardAuthor(boardDto.getBoardAuthor())
				.subject(boardDto.getSubject())
				.content(boardDto.getContent())
				.build();
	}
	
}
