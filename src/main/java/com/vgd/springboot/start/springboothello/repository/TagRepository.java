package com.vgd.springboot.start.springboothello.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vgd.springboot.start.springboothello.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
	Optional<Tag> findByName(String name);
}
