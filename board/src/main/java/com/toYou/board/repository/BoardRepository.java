package com.toYou.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.toYou.board.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>{

	Page<Board> findAll(Pageable pageable);
	
}
