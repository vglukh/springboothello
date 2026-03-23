package com.vgd.springboot.start.springboothello.services;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.vgd.springboot.start.springboothello.controller.dto.UserDTO;
import com.vgd.springboot.start.springboothello.controller.dto.mappers.Mappers;
import com.vgd.springboot.start.springboothello.entity.Task;
import com.vgd.springboot.start.springboothello.entity.User;
import com.vgd.springboot.start.springboothello.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class StartUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
	}
	
	public UserDTO loadUserById(Long userId) throws UsernameNotFoundException {
		var user = userRepository.findUserWithTasks(userId)
				.orElseThrow(() -> new UsernameNotFoundException("User not found, id = " + userId));
		
		return Mappers.toDto(user);
	}
	
	
	@Transactional
	public User addTasks(String userName, List<String> tasksDescriptions) {
		var user = loadUserByUsername(userName);
		var userTasks = tasksDescriptions.stream().map(d -> createNewTask(d, user)).collect(toList());
		
		user.getTasks().clear();
		if (userTasks != null) {
			user.getTasks().addAll(userTasks);
		}
		
		return userRepository.save(user);
	}
	
	private Task createNewTask(String taskDescription, User user) {
		Task task = new Task();
		
		task.setDescription(taskDescription);
		task.setCompleted(false);
		task.setUser(user);

		return task;
	}
}
