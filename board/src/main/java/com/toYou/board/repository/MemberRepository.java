package com.toYou.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toYou.board.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Integer>{
	
	Member findByMemberName(String memberName);
	
}
