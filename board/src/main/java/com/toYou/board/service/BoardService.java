package com.toYou.board.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.toYou.board.domain.Board;
import com.toYou.board.dto.BoardDto;
import com.toYou.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;

	private static final int PAGE_POST_COUNT = 10;

	
	// Page<BoardDto> boardPage = this.boardService.getBoardPage(pageNum);
	public Page<BoardDto> getBoardPage(int pageNum) {
		Pageable pageable = PageRequest.of(pageNum, PAGE_POST_COUNT, Sort.by(Sort.Order.desc("createdDate")));
		Page<Board> entityBoardPage = this.boardRepository.findAll(pageable);

		return entityBoardPage.map(Board::convertEntity2Dto);
	}


}
