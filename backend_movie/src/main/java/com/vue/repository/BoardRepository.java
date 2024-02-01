package com.vue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vue.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	
}
