package com.vue.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.vue.dto.MemberDto;
import com.vue.entity.Item;
import com.vue.entity.Member;
import com.vue.repository.MemberRepository;
import com.vue.service.JwtService;

import io.jsonwebtoken.Claims;

@RestController
public class AccountController {
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	JwtService jwtService;
	
	@GetMapping("/api/account/check")
	public ResponseEntity check(@CookieValue(value="token", required=false) String token){
		Claims claims = jwtService.getClaims(token);
		
		if(claims != null) {
			int id = Integer.parseInt(claims.get("id").toString());
			return new ResponseEntity<>(id, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/api/account/login")
	public ResponseEntity login(@RequestBody Map<String, String> params, HttpServletResponse res){
		Member member = memberRepository.findByEmailAndPassword(params.get("email"), params.get("password"));

		if(member != null) {
			int grade = member.getGrade();
//			int id = member.getId();
//			
//			String token = jwtService.getToken("id", id);
//			
//			Cookie cookie = new Cookie("token", token);
//			cookie.setHttpOnly(true);
//			cookie.setPath("/");
//			
//			res.addCookie(cookie);
			return new ResponseEntity<>(grade, HttpStatus.OK);
		}
		
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/api/account/mlist")
	public List<Member> memberList(){
		
		List<Member> members = memberRepository.findAll();
		
		return members;
	}
	
	@PostMapping("/api/account/join")
	public ResponseEntity join(@RequestBody MemberDto dto){
		System.out.print("/api/account/join");
		
		Member newMember = new Member();
		newMember.setName(dto.getName());
		newMember.setEmail(dto.getEmail());
		newMember.setPassword(dto.getPassword());
		newMember.setRegdate(LocalDateTime.now());
		
		memberRepository.save(newMember);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
