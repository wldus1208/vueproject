package com.vue.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.vue.entity.Advance;
import com.vue.entity.Item;
import com.vue.repository.AdvanceRepository;
import com.vue.repository.ItemRepository;
import com.vue.service.JwtService;

import lombok.Getter;

@RestController
public class AdvanceController {
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	AdvanceRepository advanceRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	@GetMapping("/api/advance/items")
	public ResponseEntity getItem(@CookieValue(value="token", required=false) String token) {
		if(jwtService.isVaild(token)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		
		int memberId = jwtService.getId(token);
		List<Advance> advances = advanceRepository.findByMemberId(memberId);
		List<Integer> itemIds = (List<Integer>) advances.stream().map(Advance::getItemId);
		List<Item> items = itemRepository.findByIdIn(itemIds);
		
		return new ResponseEntity<>(items, HttpStatus.OK);		
	}
	
	@PostMapping("/api/advance/items/{itemId}")
	public ResponseEntity pushItem(
			@PathVariable int itemId, 
			@CookieValue(value="token", required=false) String token){
		if(jwtService.isVaild(token)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}else {
			int memberId = jwtService.getId(token);
			Advance advance = advanceRepository.findByMemberIdAndItemId(memberId, itemId);
			
			if(advance == null) {
				Advance newAdvance = new Advance();
				newAdvance.setMemberId(memberId);
				newAdvance.setItemId(itemId);
				advanceRepository.save(newAdvance);
			}
			
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		
	}
}
