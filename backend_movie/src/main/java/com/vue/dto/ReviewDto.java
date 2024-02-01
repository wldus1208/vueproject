package com.vue.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

@Getter
public class ReviewDto {
	
	private int id;
	private String title;
	private String address;
	private String content;
	private int grade;
	private double lon;
	private double lat;
	List<MultipartFile> files;
}
