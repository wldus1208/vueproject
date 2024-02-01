package com.vue.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


import java.time.LocalDateTime;

@Getter
@Setter
@Table(name="notice")
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(length=100, nullable=false)
    private String gubun;
    
    @Column(length=200, nullable=false)
    private String title;
    
    @Column(length=1000, nullable=false)
    private String content;
    
    @Column
    private LocalDateTime created;
}
