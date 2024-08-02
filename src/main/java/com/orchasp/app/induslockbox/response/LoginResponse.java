package com.orchasp.app.induslockbox.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orchasp.app.induslockbox.entity.Role;

import lombok.Data;

@Data
public class LoginResponse {
	private Long id;
	private String token;
	private String type="Bearer";
	private Role role;
	private String userName;
	@JsonIgnore
	private String password;
	private String message;
	private String email;
	private String mobile;
	
}
