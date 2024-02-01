package com.vue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vue.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
	Member findByEmailAndPassword(String email, String password); 
}
