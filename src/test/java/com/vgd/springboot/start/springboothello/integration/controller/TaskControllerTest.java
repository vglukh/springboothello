package com.vgd.springboot.start.springboothello.integration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgd.springboot.start.springboothello.entity.Task;
import com.vgd.springboot.start.springboothello.repository.TaskRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@AutoConfigureMockMvc
public class TaskControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	@WithMockUser
	void shouldSaveTaskInDatabase() throws Exception {
		var newTask = new Task();
		newTask.setDescription("Integration Controller Task");
		newTask.setCompleted(false);
		
		String newTaskJson = objectMapper.writeValueAsString(newTask);
		int initialRowsSize = taskRepository.findAll().size();
		
		MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders.post("/start/api/tasks")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(newTaskJson);
		
		mockMvc.perform(postRequestBuilder).andExpect(MockMvcResultMatchers.status().isOk());
		
		int actualRowsSize = taskRepository.findAll().size();
		assertThat(actualRowsSize).isGreaterThan(initialRowsSize);
	}
	
	@Test
	@WithMockUser
	void shouldNotSaveTaskWithoutDescriptionDatabase() throws Exception {
		MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders.post("/start/api/tasks")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new Task()));
		
		mockMvc.perform(postRequestBuilder).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
}
