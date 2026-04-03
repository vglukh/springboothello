package com.vgd.springboot.start.springboothello.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.vgd.springboot.start.springboothello.controller.dto.UserDTO;

@FeignClient(name = "auth-service", url = "http://localhost:8081")
public interface UserClient {
	
	@PostMapping("/start_auth/users/login")
	String login(@RequestBody UserDTO userDto);
	
	@GetMapping("start_auth/users/findByName")
	UserDTO getByName(@RequestParam String username);
}
