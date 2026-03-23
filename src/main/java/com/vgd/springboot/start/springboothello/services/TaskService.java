package com.vgd.springboot.start.springboothello.services;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import static java.util.Objects.*;

import com.vgd.springboot.start.springboothello.controller.dto.TaskConditionDTO;
import com.vgd.springboot.start.springboothello.controller.dto.TaskDTO;
import com.vgd.springboot.start.springboothello.entity.*;
import com.vgd.springboot.start.springboothello.exceptions.TaskNotFoundException;
import com.vgd.springboot.start.springboothello.repository.*;
import static com.vgd.springboot.start.springboothello.repository.specifications.TaskSpecifications.*;

@Service
@RequiredArgsConstructor
public class TaskService {
	private final TaskRepository taskRepository;
	private final UserRepository userRepository;
	private final StartUserDetailsService userService;
	private final TagRepository tagRepository;
	
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}
	
	public Task findById(Long taskId) {
		return taskRepository.findById(taskId)
				.orElseThrow(() -> new TaskNotFoundException(taskId));
	}
	
	public List<Task> findByUserId(Long userId) {
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
		} else {
			var user = userService.loadUserByUsername(auth.getName());
			user = userRepository.findUserWithTasks(user.getId()).get();
			
			var tags = tagNames.stream()
					.map(n -> tagRepository.findByName(n).orElseGet(null))
					.filter(Objects::nonNull)
					.collect(Collectors.toSet());
			
			Task newTask = new Task();
			newTask.setDescription(taskDescription);
			newTask.setCompleted(false);
			newTask.setUser(user);
			newTask.setTags(tags);
			
			user.getTasks().add(newTask);
			return taskRepository.save(newTask);
		}
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
	
	@Transactional
	public void deleteTaskOfUser(Long id, Long userId) {
		var user = userRepository.findUserWithTasks(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		var taskOpt = user.getTasks().stream().filter(t -> t.getId().equals(id)).findAny();
		if (taskOpt.isPresent()) {
			var task = taskOpt.get();
			user.getTasks().remove(task);
			task.setUser(null);
		} else {
			throw new RuntimeException("Task not found");
		}
	}
	
	public List<Task> getDeletedTasks(Long userId) {
		return taskRepository.findDeleted(userId);
	}
}
