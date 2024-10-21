package com.toYou.board.dto;

import java.util.List;
import java.util.Set;

import com.toYou.board.domain.Answer;
import com.toYou.board.domain.Board;
import com.toYou.board.domain.Member;
import com.toYou.board.domain.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {

	private Integer id;
	private String memberName;
	private String email;
	private Role role;

	private List<Board> boardList;
	private List<Answer> answerList;

	private Set<Board> likedBoards;
	private Set<Answer> likedAnswers;

	@Builder
	public MemberDto(Integer id, String memberName, String email, Role role, List<Board> boardList,
			List<Answer> answerList, Set<Board> likedBoards, Set<Answer> likedAnswers) {
	
		this.id = id;
		this.memberName = memberName;
		this.email = email;
		this.role = role;
		
		this.boardList = boardList;
		this.answerList = answerList;
		this.likedBoards = likedBoards;
		this.likedAnswers = likedAnswers;
	}
	
}
