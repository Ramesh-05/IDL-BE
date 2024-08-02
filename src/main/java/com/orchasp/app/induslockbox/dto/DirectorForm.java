package com.orchasp.app.induslockbox.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.orchasp.app.induslockbox.entity.Company;

import lombok.Data;

@Data
public class DirectorForm {

	private String name;
	private String email;
	private Long dinNo;
	private Long aadharNo;
	private String panNo;
	private String passportNo;
	private String designation;
	private String dateOfAppointment;
	private String dateOfExit;
	private Long mobileNo;
	private String address;
	private String createdBy;
	private String updatedBy;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	private boolean active = true;
	private MultipartFile image;
	private long companyid;
	
	private Company company;

}
