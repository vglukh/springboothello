package com.vgd.springboot.start.springboothello.entity;

import java.util.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class UserPrincipal extends User {
	private static final long serialVersionUID = 4198388211917585407L;
	
	private final UUID uuid;

	public UserPrincipal(UUID uuid, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.uuid = uuid;
	}
}
