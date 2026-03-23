package com.vgd.springboot.start.springboothello.exceptions;

public class TaskNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -1026660785792597518L;

	public TaskNotFoundException(Long id) {
		super("Task with ID=" + id + " not found");
	}
}
