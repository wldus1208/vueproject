package com.vue.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.vue.dto.BoardDto;
import com.vue.entity.Board;
import com.vue.repository.BoardRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {
	private final BoardRepository boardRepository;
    
    /**
     * 게시글 가져오기
     */
    public BoardDto getBoard(int id) {
        Board entity = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        return BoardDto.builder()
        		.id(entity.getId())
                .gubun(entity.getGubun())
                .title(entity.getTitle())
                .content(entity.getContent())
                .created(entity.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                .build();
    }
}
