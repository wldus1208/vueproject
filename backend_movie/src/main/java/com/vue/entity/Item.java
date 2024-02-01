package com.vue.entity;

import javax.persistence.*;

import lombok.Getter;

@Getter
@Entity
@Table(name="items")
public class Item {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(length=50, nullable=false)
	private String name;
	
	@Column(length=50)
	private String director;
	
	@Column(length=100)
	private String type;
	
	@Column(length=600)
	private String content;
	
	@Column(length=100)
	private String people;
	
	@Column(length=100)
	private String imgPath;
	
	@Column(length=100)
	private String opendate;
	
}
