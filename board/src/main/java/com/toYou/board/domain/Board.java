package com.toYou.board.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.toYou.board.dto.BoardDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * 
1. 엔티티 매핑
JPA는 "객체와 데이터베이스 테이블 간의 매핑"을 통해 
관계형 데이터베이스에 저장된 "데이터를 객체 형태로 쉽게 관리"할 수 있게 해줌.
각 JPA 엔티티는 데이터베이스의 한 테이블에 매핑되며, 엔티티의 필드는 테이블의 열(column)에 매핑됨.

2. 인스턴스 생성
JPA가 데이터베이스에서 엔티티를 *조회*할 때, 해당 "엔티티의 인스턴스를 생성해야 함."
이 때 JPA는 리플렉션(reflection)을 사용하여 기본 생성자를 호출합니다.
기본 생성자가 없으면 JPA는 인스턴스를 생성할 수 없기 때문에 **InstantiationException**이 발생합니다.

3. 필드 초기화
기본 생성자로 객체가 생성된 후, JPA는 매핑된 필드에 대해 데이터베이스에서 조회한 값을 설정합니다.
이 과정은 보통 JPA가 내부적으로 관리합니다. 예를 들어, 데이터베이스에서 Board 테이블의 데이터가
SELECT 쿼리를 통해 조회되면, (1) JPA는 기본 생성자를 호출하여 Board 객체를 생성한 뒤, (NoArgsConstructor)
결과 집합에서 가져온 값(레코드?)으로 해당 객체의 필드를 채웁니다.

4. 리플렉션의 사용
기본 생성자를 사용하여 객체를 인스턴스화한 후, 필드에 직접 접근하여 값을 설정할 수도 있습니다.
이 경우, 필드는 접근 제어자가 private인 경우에도 설정할 수 있습니다. 
	
	4-1. 기본 생성자 호출 : JPA는 엔티티 클래스의 기본 생성자를 호출해서 객체를 생성함. (이래서 기본생성자가 필요함)
	4-2. 리플렉션을 통한 필드 접근 (Not Setter!!!) : JPA는 기본 생성자를 통해 생성한 객체의 필드 값을 
	     설정하기 위해 리플렉션을 사용하여 private 필드에 접근할 수 있음.
	4-3. 값 설정 : 결과 집합에서 가져온 값 으로 필드를 설정함.

 */

@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 추가.
@EntityListeners(value = { AuditingEntityListener.class }) //엔티티가 생성될 때, createdDate 필드에 자동으로 값을 채워넣음.
@Getter
@Entity
public class Board {

	// ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// BOARD_AUTHOR_ID
	@ManyToOne
	@JoinColumn(name = "BOARD_AUTHOR_ID")
	private Member boardAuthor;

	// SUBJECT
	@Column(length = 60, nullable = false)
	private String subject;

	// CONTENT
	@Lob // Large Object
	@Column(nullable = false)
	private String content;

	// VIEW_COUNT
	@Column
	private Integer viewCount = 0;

	@OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
	private List<Answer> answerList = new ArrayList<>();

	// BOARD_LIKES = [MEMBER_ID|BOARD_ID]
	// 두 엔티티 사이에 다대다 관계를 표현, 중간 테이블(조인 테이블)을 통해서 이 관계를 매핑함.
	@ManyToMany(cascade = CascadeType.REMOVE)
	@JoinTable(name = "board_likes",
			   joinColumns = @JoinColumn(name = "BOARD_ID"), 
			   inverseJoinColumns = @JoinColumn(name = "MEMBER_ID"))
	private Set<Member> likedUsers = new HashSet<>();

	// CREATED_TIME
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdDate;

	// LAST_MODIFIED
	@Column
	private LocalDateTime lastModified;
	
	
	// User-Level =======> DataBase
	@Builder
	public Board(Member boardAuthor, String subject, String content) {
		
//		사용자가 DTO에 어느 내용을 쳐넣었겠지? 
//		근데 그 내용이 DTO 객체의 setter를 통해 필드값이 채워지겠지?
//		그 값을 파라미터로 DTO 객체에서 toEntity()를 호출해서 찐 Entity를 생성
//
//		public static Board convertDto2Entity(BoardDto boardDto) {
//			
//			return Board.builder()
//					.boardAuthor(boardDto.getBoardAuthor())
//					.subject(boardDto.getSubject())
//					.content(boardDto.getContent())
//					.build();
//		}
		this.boardAuthor = boardAuthor;
		this.subject = subject;
		this.content = content;
	}

	
	// User-Level <======= DataBase
	
	// getBoardPage 메서드에서 Page<Board>를 가지고 올때,
	// @Entity를 그대로 렌더링 하는 것이 아닌, 데이터 전달 객체인 DTO로 내용을 복사해서 전달해야 함.
	public static BoardDto convertEntity2Dto(Board boardEntity) {
		return BoardDto.builder()
				.id(boardEntity.getId())
				.boardAuthor(boardEntity.getBoardAuthor())
				.subject(boardEntity.getSubject())
				.content(boardEntity.getContent())
				.createdDate(boardEntity.getCreatedDate())
				.lastModified(boardEntity.getLastModified())
				.viewCount(boardEntity.getViewCount())
				.answerList(boardEntity.getAnswerList())
				.likedUsers(boardEntity.getLikedUsers())
				.build();
	}
}