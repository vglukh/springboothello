package com.vgd.springboot.start.springboothello.services;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.vgd.springboot.start.springboothello.controller.dto.UserDTO;
import com.vgd.springboot.start.springboothello.entity.Task;
import com.vgd.springboot.start.springboothello.repository.TaskRepository;
import com.vgd.springboot.start.springboothello.services.utils.SecurityUtils;

@Service
@RequiredArgsConstructor
public class StartUserDetailsService {
    TaskRepository taskRepository;
	
	// TODO
	public UserDTO loadUserById(Long userId) throws UsernameNotFoundException {
		/*var user = userRepository.findUserWithTasks(userId)
				.orElseThrow(() -> new UsernameNotFoundException("User not found, id = " + userId));*/
		
		return null;
	}
	
	
	@Transactional
	public void addTasks(String userName, List<String> tasksDescriptions) {
		var userPrincipal = SecurityUtils.getAuthorizedUser();
		var userTasks = tasksDescriptions.stream().map(d -> createNewTask(d, userPrincipal.getUuid())).collect(toList());
		userTasks.forEach(taskRepository::save);
	}
	
	private Task createNewTask(String taskDescription, UUID userId) {
		Task task = new Task();
		
		task.setDescription(taskDescription);
		task.setCompleted(false);
		task.setUserId(userId);

		return task;
	}
}
