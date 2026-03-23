package com.vgd.springboot.start.springboothello.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.vgd.springboot.start.springboothello.entity.Task;
import com.vgd.springboot.start.springboothello.repository.TaskRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryTest {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Test
	void shouldCreateAndSearchTask() {
		var testTask = new Task();
		testTask.setDescription("Task for JPA test");
		testTask.setCompleted(false);

		var savedTask = taskRepository.save(testTask);
		var foundTast = taskRepository.findById(savedTask.getId());
		
		assertThat(foundTast).isPresent();
		assertThat(foundTast.get().getDescription()).isEqualTo(testTask.getDescription());
	}

}
