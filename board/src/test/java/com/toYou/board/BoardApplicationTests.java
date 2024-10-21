package com.toYou.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.toYou.board.domain.Answer;
import com.toYou.board.domain.Board;
import com.toYou.board.domain.Member;
import com.toYou.board.domain.Role;
import com.toYou.board.repository.AnswerRepository;
import com.toYou.board.repository.BoardRepository;
import com.toYou.board.repository.MemberRepository;

@SpringBootTest
class BoardApplicationTests {

    @Autowired
    private MemberRepository userRepository;
    
    @Autowired
    private BoardRepository boardRepository;
    
    @Autowired
    private AnswerRepository answerRepository;
    
	@Test
	public void contextLoads() {
	
		generationMember();
		generationBoard();
		generationAnswer();
		
	}

	private void buildMember(String memberName, String password, String email, Role role) {
		Member member = Member.builder()
							.memberName(memberName)
							.password(password)
							.email(email)
							.role(role)
							.build();
		
		this.userRepository.save(member);
	}

	
	private void buildAnswer(String content, String authorName, Integer board_Id) {
		
		Member author = this.userRepository.findByMemberName(authorName);
		Board board = this.boardRepository.findById(board_Id).get();
		
		Answer answer = Answer.builder()
							.content(content)
							.answerAuthor(author)
							.board(board)
							.build();
		
		this.answerRepository.save(answer);
	}
	
	private void buildBoard(String memberName, String subject, String content) {
		
		Member author = this.userRepository.findByMemberName(memberName);
		
		Board board = Board.builder()
						.boardAuthor(author)
						.subject(subject)
						.content(content)
						.build();
		
		this.boardRepository.save(board);
	}
	

	private void generationMember() {
		buildMember("Anna", "2848", "a@f", Role.USER);
		buildMember("Erma", "2848", "d@f", Role.USER);
		buildMember("Lunisha", "2848", "e@f", Role.USER);
		buildMember("Romi", "2848", "f@f", Role.USER);
	}
	
	private void generationAnswer() {
		for(int i=1; i<=40; i++) {
			buildAnswer("테스트 댓글", "Anna", i);
		}
	}

	private void generationBoard() {
		
		for(int i=0; i<10; i++) {
			buildBoard("Anna", "테스트 제목", "테스트 내용");
			buildBoard("Erma", "테스트 제목", "테스트 내용");
			buildBoard("Romi", "테스트 제목", "테스트 내용");
			buildBoard("Lunisha", "테스트 제목", "테스트 내용");
		}
		
	}
	
}
