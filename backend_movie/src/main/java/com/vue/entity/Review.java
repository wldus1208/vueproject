package com.vue.entity;

import java.util.List;

import javax.persistence.*;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="reviews")
public class Review {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(length=20, nullable=false)
	private String title;
	
	@Column(length=500, nullable=false)
	private String content;
	
	@Column(length=100, nullable=false)
	private String address;
	
	@Column
	private int grade;
	
	@Column
	private double lat;
	
	@Column
	private double lon;
	
}
