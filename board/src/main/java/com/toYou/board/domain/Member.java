package com.toYou.board.domain;

import java.util.*;

import com.toYou.board.dto.MemberDto;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부에서의 생성을 열어 둘 필요가 없을 때 / 보안적으로 권장된다.
@Getter
@Entity
public class Member {

	// ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// MEMBER_NAME
	@Column(nullable = false, unique = true)
	private String memberName;

	// PASSWORD
	@Column(nullable = false)
	private String password;

	// EMAIL
	@Column(nullable = false, unique = true)
	private String email;

	//ROLE
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	
	@OneToMany(mappedBy = "boardAuthor", cascade = CascadeType.REMOVE)
	private List<Board> boardList = new ArrayList<>();

	@OneToMany(mappedBy = "answerAuthor", cascade = CascadeType.REMOVE)
	private List<Answer> answerList = new ArrayList<>();

	// BOARD_LIKES
	// [MEMBER_ID|BOARD_ID]
	@ManyToMany(cascade = CascadeType.REMOVE)
	@JoinTable(
			name = "board_likes", 
			joinColumns = @JoinColumn(name = "MEMBER_ID"), 
			inverseJoinColumns = @JoinColumn(name = "BOARD_ID")
	)
	private Set<Board> likedBoards = new HashSet<>();

	// ANSWER_LIKES
	@ManyToMany(cascade = CascadeType.REMOVE)
	@JoinTable(
			name = "answer_likes",
			joinColumns = @JoinColumn(name = "MEMBER_ID"), 
			inverseJoinColumns = @JoinColumn(name = "ANSWER_ID")
	)
	private Set<Answer> likedAnswers = new HashSet<>();

	@Builder
	public Member(String memberName, String password, String email, Role role) {
		this.memberName = memberName;
		this.password = password;
		this.email = email;
		this.role = role;
	}
	
	public static MemberDto convertEntity2Dto(Member member) {
		return MemberDto.builder()
				.id(member.getId())
				.memberName(member.getMemberName())
				.email(member.getEmail())
				.role(member.getRole())
				.boardList(member.getBoardList())
				.answerList(member.getAnswerList())
				.likedAnswers(member.getLikedAnswers())
				.likedBoards(member.getLikedBoards())
				.build();
	}


	
}
