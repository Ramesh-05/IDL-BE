package com.orchasp.app.induslockbox.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.orchasp.app.induslockbox.entity.Company;

import lombok.Data;

@Data
public class KMPDTO {

	private String name;

	private String email;

	private Long aadharNo;

	private String passportNo;

	private String panNo;

	private String designation;

	private String address;

	private String state;

	private MultipartFile image;
	
	private MultipartFile resume;

	private String mobileNo;
	
	private String createdBy;
	
	private String updatedBy;
	
	private LocalDateTime createdDate;
	
	private LocalDateTime updatedDate;
	
	private boolean active = true;

	private Long companyid;
	
	private Company company;
	
}
