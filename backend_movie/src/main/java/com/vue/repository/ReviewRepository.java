package com.vue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vue.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
