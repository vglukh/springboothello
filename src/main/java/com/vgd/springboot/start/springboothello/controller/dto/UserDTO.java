package com.vgd.springboot.start.springboothello.controller.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO implements UserDetails {
	private static final long serialVersionUID = 7064123095197188818L;

	private UUID id;
	
	@JsonProperty("username")
    private String username;
	
	private String password;
	private Set<String> roles = new HashSet<>();
	
	private List<TaskDTO> tasks = new ArrayList<>();
	
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
    }
}
