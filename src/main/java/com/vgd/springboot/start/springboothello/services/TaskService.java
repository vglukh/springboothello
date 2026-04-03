package com.vgd.springboot.start.springboothello.services;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import static java.util.Objects.*;

import com.vgd.springboot.start.springboothello.controller.dto.*;
import com.vgd.springboot.start.springboothello.entity.*;
import com.vgd.springboot.start.springboothello.exceptions.TaskNotFoundException;
import com.vgd.springboot.start.springboothello.repository.*;
import com.vgd.springboot.start.springboothello.services.utils.SecurityUtils;

import static com.vgd.springboot.start.springboothello.services.utils.SecurityUtils.getAuthorizedUser;
import static com.vgd.springboot.start.springboothello.repository.specifications.TaskSpecifications.*;

@Service
@RequiredArgsConstructor
public class TaskService {
	private final TaskRepository taskRepository;
	private final TagRepository tagRepository;
	
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}
	
	public Task findById(Long taskId) {
		return taskRepository.findById(taskId)
				.orElseThrow(() -> new TaskNotFoundException(taskId));
	}
	
	public List<Task> findByUserId(UUID userId) {
		return taskRepository.findByUserId(userId);
	}
	
	public List<Task> findByCondition(TaskConditionDTO condition) {
		Specification<Task> taskSpec = filter(condition);
		return taskRepository.findAll(taskSpec);
	}
	
	public Task createTask(String taskDescription, List<String> tagNames) {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		if (isNull(auth) || !auth.isAuthenticated()) {
			throw new AccessDeniedException("Access Denied!");
		}

		var tags = tagNames.stream()
				.map(n -> tagRepository.findByName(n).orElseGet(null))
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
		
		Task newTask = new Task();
		newTask.setDescription(taskDescription);
		newTask.setCompleted(false);
		newTask.setUserId(getAuthorizedUser().getUuid());
		newTask.setTags(tags);
		
		return taskRepository.save(newTask);
	}
	
	
	public Task updateTask(Long id, TaskDTO updatedTask) {
		return taskRepository.findById(id).map(task -> {
			task.setDescription(updatedTask.getDescription());
			task.setCompleted(updatedTask.isCompleted());
			return taskRepository.save(task);
		}).orElseThrow(() -> new TaskNotFoundException(id));
	}
	
	public void deleteTask(Long id) {
		taskRepository.deleteById(id);
	}
	
	public void deleteTaskOfUser(Long id) {
		var userId = SecurityUtils.getAuthorizedUser().getUuid();
		
		var taskOpt = taskRepository.findByUserId(userId)
				.stream()
				.filter(t -> t.getId().equals(id))
				.findAny();
		
		if (taskOpt.isPresent()) {
			taskRepository.deleteById(taskOpt.get().getId());
		} else {
			throw new RuntimeException("Task not found");
		}
	}
	
	public List<Task> getDeletedTasks(Long userId) {
		return taskRepository.findDeleted(userId);
	}
}
