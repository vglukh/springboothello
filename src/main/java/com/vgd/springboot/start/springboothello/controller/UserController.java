package com.vgd.springboot.start.springboothello.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vgd.springboot.start.springboothello.controller.dto.UserDTO;
import com.vgd.springboot.start.springboothello.feignclients.UserClient;
import com.vgd.springboot.start.springboothello.services.StartUserDetailsService;



@RestController
@RequestMapping("/start/api/user")
@AllArgsConstructor
public class UserController {
	private final StartUserDetailsService userService;
	private final UserClient userClient;
	
	@PostMapping("/add_tasks")
	public ResponseEntity<UserDTO> addTasks(@RequestParam String userName, @RequestBody List<String> tasks) {
		var user = userService.addTasks(userName, tasks);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/{userId}/search_with_tasks")
	public ResponseEntity<UserDTO> findUserById(@PathVariable Long userId) {
		var user = userService.loadUserById(userId);
		return ResponseEntity.ok(user);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserDTO userDto) {
		return ResponseEntity.ok(userClient.login(userDto)) ;
	}
}
