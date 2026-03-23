package com.vgd.springboot.start.springboothello.configuration;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableJpaAuditing
public class JpaConfiguration {
	
	@Bean
	AuditorAware<String> auditorProvider() {
		return () -> {
			var authentication = SecurityContextHolder.getContext().getAuthentication();
			return Optional.ofNullable(authentication).filter(Authentication::isAuthenticated)
					.map(Authentication::getName);
		};
	}
}
