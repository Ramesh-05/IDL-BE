package com.orchasp.app.induslockbox.request;

import com.orchasp.app.induslockbox.entity.Role;

import lombok.Data;

@Data
public class SignUpRequest {
	private String userName;
	private String email;
	private String password;
	private String mobile;
//	private String firstName;
//	private String lastName;
//	private String displayName;
//	private  byte[] profile_pic;
//	private Timestamp dob;
//	private String address;
//	private int status;
//	private Long createdBy;
//	private Long updatedBy;
	private Role role;
	
	
}
