package com.orchasp.app.induslockbox.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class CompanyDTO {

	 private String companyCode;
	    private String cin;
	    private String companyname;
	    private String dateOfIncorporation;
	    private String registerNo;
	    private String telephoneNo;
	    private String email;
	    private String address;
	    private String website;
	    private String phoneNo;
	    private String faxNo;
	    private String city;
	    private String state;
	    private String pincode;
	    private MultipartFile logoName;
	    private String createdBy;
	    private String updatedBy;
	    private LocalDateTime createdDate;
	    private LocalDateTime updatedDate;
	    private boolean active = true;
}
