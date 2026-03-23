package com.vgd.springboot.start.springboothello.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Tag")
public class Tag {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Byte id;
	
	@Column(unique = true, nullable = true)
	private String name;
	
	@ManyToMany(mappedBy = "tags")
	@JsonBackReference
	private Set<Task> tasks = new HashSet<Task>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TagCategoryId")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private TagsCategory category;
}
