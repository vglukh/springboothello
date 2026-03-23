package com.vgd.springboot.start.springboothello.exceptions.handlers;

import java.time.LocalDateTime;
import java.util.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.vgd.springboot.start.springboothello.exceptions.TaskNotFoundException;

@RestControllerAdvice
public class StartExceptionHandler {
	
	@ExceptionHandler(TaskNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String> handleTaskNotFound(TaskNotFoundException exception) {
		return Map.of("error", exception.getMessage(),
				"timestamp", LocalDateTime.now().toString());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handleValidationException(MethodArgumentNotValidException exception) {
		Map<String, String> errors = new HashMap<>();
		exception.getBindingResult()
		    .getFieldErrors()
		    .forEach(error -> errors.put("Invalid field " + error.getField(), error.getDefaultMessage()));
		return errors;
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDeniedException() {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(Map.of("error", "Authentification failed"));
	}
	
	public ResponseEntity<String> handleConflict(ObjectOptimisticLockingFailureException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body("Data was modified by another user");
	}
}
