package com.vgd.springboot.start.springboothello.controller.dto.mappers;

import lombok.*;

import static java.util.stream.Collectors.toList;

import com.vgd.springboot.start.springboothello.controller.dto.*;
import com.vgd.springboot.start.springboothello.entity.*;

public class Mappers {
	private Mappers() {}
	
	public static UserDTO toDto(User user) {
		var tasks = user.getTasks().stream().map(Mappers::toDto).collect(toList());
		return UserDTO.builder().username(user.getUsername()).role(user.getRole()).tasks(tasks).build();
	}
	
	public static TaskDTO toDto(Task task) {
		return TaskDTO.builder()
				.id(task.getId())
				.completed(task.isCompleted())
				.description(task.getDescription())
				.build();
	}
}
