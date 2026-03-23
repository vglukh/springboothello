package com.vgd.springboot.start.springboothello.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TaskConditionDTO {
	private Long id;
	private Boolean completed;
	private String tagName;
	private String description;
	private Long userId;
}
