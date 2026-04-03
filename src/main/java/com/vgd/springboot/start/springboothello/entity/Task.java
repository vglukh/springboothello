package com.vgd.springboot.start.springboothello.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.*;

@Schema(description = "Task entity")
@Table(name = "task")
@Entity
@NamedEntityGraph(
		name = "Task.withTagsAndUser",
        attributeNodes = {@NamedAttributeNode(value = "tags", subgraph = "tags_category_subgraph")},
        subgraphs = @NamedSubgraph(name = "tags_category_subgraph", attributeNodes = @NamedAttributeNode("category"))
)
@SQLDelete(sql = "UPDATE task SET deleted = 1 WHERE id = ? AND version = ?")
@SQLRestriction("deleted = 0")
@Getter
@Setter
@NoArgsConstructor
public class Task extends BaseEntity {
	
	@Schema(description = "Task identificator")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Schema(description = "Task description")
	@NotBlank(message = "Description shoul not be empty")
	@Size(min = 3, max = 100, message = "3 ... size ... 100")
	private String description;
	
	private boolean completed;
	
	@Column(nullable = false)
	private boolean deleted = false;
	
	@Column(name = "user_id")
	private UUID userId;
	
	@JsonManagedReference
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "Task_tag", joinColumns = @JoinColumn(name = "TaskId"), inverseJoinColumns = @JoinColumn(name = "TagId"))
	private Set<Tag> tags = new HashSet<Tag>();
}
