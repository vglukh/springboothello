package com.vgd.springboot.start.springboothello.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "TagsCategory")
public class TagsCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short id;
	
	@Column(unique = true, nullable = true)
	private String name;
}
