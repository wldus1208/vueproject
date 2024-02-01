package com.vue.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.vue.dto.ReviewDto;
import com.vue.entity.Board;
import com.vue.entity.Review;
import com.vue.repository.ReviewRepository;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@GetMapping("/getReviews")
	public List<Review> getReviews(){
		
		List<Review> review = reviewRepository.findAll();
		
		return review;
	}
	
	@PostMapping("/saveReview")
	public ResponseEntity join(@RequestBody ReviewDto dto){
		
		Review newReview = new Review();
		newReview.setTitle(dto.getTitle());
		newReview.setAddress(dto.getAddress());
		newReview.setContent(dto.getContent());
		newReview.setGrade(dto.getGrade());
		newReview.setLon(dto.getLon());
		newReview.setLat(dto.getLat());
		
		reviewRepository.save(newReview);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteReviews/{id}")
    public ResponseEntity delete(@PathVariable int id) {
    	Review entity = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("글을 찾을 수 없습니다."));
    	reviewRepository.delete(entity);
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	
}
