package com.vgd.springboot.start.springboothello.repository.specifications;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import java.util.*;

import static org.springframework.util.StringUtils.hasText;
import static java.util.Objects.nonNull;

import com.vgd.springboot.start.springboothello.controller.dto.TaskConditionDTO;
import com.vgd.springboot.start.springboothello.entity.Task;

public class TaskSpecifications {
	
	public static Specification<Task> filter(TaskConditionDTO condition) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			if (nonNull(condition.getCompleted())) {
				predicates.add(cb.equal(root.get("completed"), condition.getCompleted()));
			}
			
			if (nonNull(condition.getUserId())) {
				predicates.add(cb.equal(root.get("user").get("id"), condition.getUserId()));
			}
			
			if (hasText(condition.getTagName())) {
				predicates.add(cb.equal(root.join("tags").get("name"), condition.getTagName()));
			}
			
			if (hasText(condition.getDescription())) {
				predicates.add(cb.like(root.get("description"), "%" + condition.getDescription() + "%"));
			}
			
			if (Long.class != query.getResultType()) {
				query.distinct(true);
				root.fetch("user", JoinType.INNER);
				root.fetch("tags", JoinType.LEFT);
			}
			
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}
