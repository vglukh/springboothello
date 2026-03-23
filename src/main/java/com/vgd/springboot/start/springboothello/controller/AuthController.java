package com.vgd.springboot.start.springboothello.controller;

import java.nio.file.AccessDeniedException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vgd.springboot.start.springboothello.controller.dto.LoginDTO;
import com.vgd.springboot.start.springboothello.controller.dto.UserDTO;
import com.vgd.springboot.start.springboothello.entity.User;
import com.vgd.springboot.start.springboothello.repository.UserRepository;
import com.vgd.springboot.start.springboothello.services.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/start/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	
	
	@PostMapping("/create")
	public ResponseEntity<String> addUser(@RequestBody UserDTO userDto) {
		var userOpt = userRepository.findByUsername(userDto.getUsername());
		if (userOpt.isPresent()) {
			return ResponseEntity.badRequest().body("User exists");
		}
		
		User user = new User(userDto);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("ROLE_USER");
		userRepository.save(user);
		return ResponseEntity.ok("User added to DB") ;
	}
	
	@PutMapping("/reset")
	public void resetPassword(@RequestParam Long userId, @RequestParam String newPassword) throws AccessDeniedException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new AccessDeniedException("User not Found!"));
		
		user.setPassword(passwordEncoder.encode(newPassword) );
		userRepository.save(user);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
		try {
			var authentication = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
			authenticationManager.authenticate(authentication);
			String token = jwtService.generateToken(loginDTO.getUsername());
			System.out.println(loginDTO.getUsername() + " logged");
			return ResponseEntity.ok(Map.of("token", token));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access deny " + e.getMessage());
		}
	}
}
