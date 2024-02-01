package com.vue.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="members")
public class Member {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(length=50, nullable=false)
	private String name;
	
	@Column(length=100, nullable=false)
	private String email;
	
	@Column(length=100, nullable=false)
	private String password;
	
	@Column
	private LocalDateTime regdate;
	
	@Column
	private int grade;
	
}
