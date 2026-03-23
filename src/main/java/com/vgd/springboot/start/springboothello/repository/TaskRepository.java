package com.vgd.springboot.start.springboothello.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vgd.springboot.start.springboothello.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
	
	@Query("SELECT t FROM Task t JOIN FETCH t.user WHERE t.completed = :completed")
	List<Task> findByCompleted(@Param("completed") boolean completed);
	
	@Query(value = "SELECT * FROM task WHERE user_id = :userId AND deleted = 1", nativeQuery = true)
	List<Task> findDeleted(Long userId);
	
	@EntityGraph(attributePaths = {"user", "tags"})
	Optional<Task> findById(Long taskId);
	
	@EntityGraph(value = "Task.withTagsAndUser")
	List<Task> findByUserId(Long userId);
}
