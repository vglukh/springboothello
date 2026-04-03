package com.vgd.springboot.start.springboothello.controller.dto.mappers;

import com.vgd.springboot.start.springboothello.controller.dto.*;
import com.vgd.springboot.start.springboothello.entity.*;

public class Mappers {
	private Mappers() {}
	
	public static TaskDTO toDto(Task task) {
		return TaskDTO.builder()
				.id(task.getId())
				.completed(task.isCompleted())
				.description(task.getDescription())
				.build();
	}
}
