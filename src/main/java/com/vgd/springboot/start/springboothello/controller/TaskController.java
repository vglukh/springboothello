package com.vgd.springboot.start.springboothello.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.vgd.springboot.start.springboothello.controller.dto.TaskConditionDTO;
import com.vgd.springboot.start.springboothello.controller.dto.TaskDTO;
import com.vgd.springboot.start.springboothello.entity.*;
import com.vgd.springboot.start.springboothello.services.TaskService;

@Tag(name = "Tasks", description = "Task manager")
@RestController
@RequiredArgsConstructor
@RequestMapping("/start/api/tasks")
public class TaskController {
	private final TaskService taskService;
	
	@GetMapping
	public List<Task> getAllTasks() {
		return taskService.getAllTasks();
	}
	
	@GetMapping("/search_status")
	public List<Task> getTasksByStatus(@RequestBody TaskConditionDTO condition) {
		return taskService.findByCondition(condition);
	}
	
	@GetMapping("/search_user/{userId}")
	public List<Task> getTasksByUser(@PathVariable UUID userId) {
		return taskService.findByUserId(userId);
	}
	
	@GetMapping("/search/{id}")
	public Task getTaskById(@PathVariable Long id) {
		return taskService.findById(id);
	}
	
	@GetMapping("/search_deleted")
	public List<Task> getDeletedTasks(@RequestParam Long userId) {
		return taskService.getDeletedTasks(userId);
	}
	
	@Operation(summary = "Create task", description = "Creates new task")
	@PostMapping("/create")
	public Task crateTask(@RequestBody TaskDTO task) {
		return taskService.createTask(task.getDescription(), task.getTags());
	}
	
	@PutMapping("/{id}")
	public Task updateTask(@Valid @PathVariable Long id, @RequestBody TaskDTO updatedTask) {
		return taskService.updateTask(id, updatedTask);
	}
	
	@DeleteMapping("/{id}")
	public void deleteTask(@PathVariable Long id) {
		taskService.deleteTask(id);
	}
	
	@DeleteMapping("/delete")
	public void deleteTaskOfUser(@RequestParam Long id) {
		taskService.deleteTaskOfUser(id);
	}
}
