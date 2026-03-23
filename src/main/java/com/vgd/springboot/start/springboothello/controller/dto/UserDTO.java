package com.vgd.springboot.start.springboothello.controller.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class UserDTO {
	
	@JsonProperty("username")
    private String username;
	
	private String password;
	private String role;
	
	@Builder.Default
	private List<TaskDTO> tasks = new ArrayList<>();
}
