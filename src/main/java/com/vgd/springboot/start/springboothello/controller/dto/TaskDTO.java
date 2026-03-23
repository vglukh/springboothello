package com.vgd.springboot.start.springboothello.controller.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class TaskDTO {
	private Long id;
	private boolean completed;
	private String description;
	private List<String> tags;
}
