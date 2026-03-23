package com.vgd.springboot.start.springboothello.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vgd.springboot.start.springboothello.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.tasks WHERE u.id = :id")
	Optional<User> findUserWithTasks(@Param("id") Long userId);
}
