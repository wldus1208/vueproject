package com.vue.controller;

import com.vue.dto.BoardDto;
import com.vue.dto.MemberDto;
import com.vue.entity.Board;
import com.vue.entity.Member;
import com.vue.repository.BoardRepository;
import com.vue.service.BoardService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class BoardController {
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	BoardService boardService;

	@GetMapping("/api/board/blist")
    public List<Board> bList(){
		
		List<Board> board = boardRepository.findAll();
		
		return board;
	}

	@GetMapping("/board/{id}")
    public BoardDto getBoard(@PathVariable int id) {
        return boardService.getBoard(id);
    }
	
	@PostMapping("/api/board")
	public ResponseEntity create(@RequestBody BoardDto boardDto){
		
		Board newBoard = new Board();
		newBoard.setGubun(boardDto.getGubun());
		newBoard.setTitle(boardDto.getTitle());
		newBoard.setContent(boardDto.getContent());
		newBoard.setCreated(LocalDateTime.now());
		
		boardRepository.save(newBoard);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

    @PatchMapping("/api/board")
    public Board update(@RequestBody BoardDto boardDto) {
    	Board entity = boardRepository.findById(boardDto.getId()).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        entity.setGubun(boardDto.getGubun());
        entity.setTitle(boardDto.getTitle());
        entity.setContent(boardDto.getContent());
        
        boardRepository.save(entity);
        
        return entity;
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseEntity delete(@PathVariable int id) {
    	Board entity = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        boardRepository.delete(entity);
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
