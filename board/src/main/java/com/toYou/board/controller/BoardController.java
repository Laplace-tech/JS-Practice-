package com.toYou.board.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.toYou.board.dto.BoardDto;
import com.toYou.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class BoardController {

	private final BoardService boardService;

	
//	th:href="@{/board/list(page=${boardPage.number-1})}
//	th:href="@{/board/list(page=${page})}
//	th:href="@{/board/list(page=${boardPage.number+1})}
	@GetMapping("/list")
	public String board(Model model, 
			@RequestParam(value = "page", defaultValue = "0") int pageNum) {
		
		Page<BoardDto> boardPage = this.boardService.getBoardPage(pageNum);

//		th:each="board : ${boardPage.content} boardPage에 객체 입히기
		model.addAttribute("boardPage", boardPage);
		
//		board_list.html로 ㄱㄱ
		return "board_list";
	}

}