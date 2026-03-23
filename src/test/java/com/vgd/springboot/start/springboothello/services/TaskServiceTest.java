package com.vgd.springboot.start.springboothello.services;

import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgd.springboot.start.springboothello.controller.dto.TaskConditionDTO;
import com.vgd.springboot.start.springboothello.entity.*;
import com.vgd.springboot.start.springboothello.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
	
	@Mock
	private TaskRepository taskRepository;
	
	@InjectMocks
	private TaskService taskService;
	
	Task mockTask = createTestTask();
	
	private Task createTestTask() {
		Task task = new Task();
		task.setId(10L);
		task.setDescription("Mockito Test Task");
		task.setCompleted(false);
		return task;
	}
	
	@Test
	void shouldReturnTaskById() {
		Mockito.when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
		
		var condition = new TaskConditionDTO();
		condition.setId(10L);
		List<Task> tasks = taskService.findByCondition(condition);
		
		assertThat(tasks.size()).isEqualTo(1);
		assertThat(tasks.get(0).getDescription()).isEqualTo("Mockito Test Task");
	}
	
	@Test
	void shouldReturnTaskByStatus() {
		Mockito.when(taskRepository.findByCompleted(false)).thenReturn(List.of(mockTask));
		
		var condition = new TaskConditionDTO();
		condition.setCompleted(false);
		List<Task> tasks = taskService.findByCondition(condition);
		
		assertThat(tasks.size()).isEqualTo(1);
		assertThat(tasks.get(0).getDescription()).isEqualTo("Mockito Test Task");
	}
}
