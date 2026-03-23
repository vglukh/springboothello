package com.vgd.springboot.start.springboothello.entity;

import java.time.LocalDateTime;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.*;
import jakarta.persistence.Version;
import lombok.*;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class BaseEntity {
	
	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@LastModifiedDate
	@Column(name = "updated_at", insertable = false)
	private LocalDateTime updatedAt;
	
	@CreatedBy
	@Column(name = "created_by", updatable = false)
	private String createdBy;
	
	@Version
	private Long version;
}
