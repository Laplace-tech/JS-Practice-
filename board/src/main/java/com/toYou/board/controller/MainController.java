package com.toYou.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {
	
	// 웹 애플리케이션에서 클라이언트와 서버 간의 상태를 유지하기 위해 사용
	private final HttpSession httpSession;
	
	@GetMapping("/")
	public String root(Model model) {
		return "redirect:/board/list";
	}	
	
}
