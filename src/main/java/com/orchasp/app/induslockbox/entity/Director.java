package com.orchasp.app.induslockbox.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "director_table")
public class Director {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "director_id")
	private Long id;
	@Column(name = "director_name")
	private String name;
	@Column(name = "director_email")
	@Email
	private String email;
	@Column(name = "din_no")
	private Long dinNo;
	@Column(name = "aadhar_no")
	private Long aadharNo;
	@Column(name = "pan_no")
	private String panNo;
	@Column(name = "passport_no")
	private String passportNo;
	@Column(name = "designation")
	private String designation;
	@Column(name = "date_of_appointment")
	private String dateOfAppointment;
	@Column(name = "date_of_exit")
	private String dateOfExit;
	@Column(name = "mobile_no")
	private Long mobileNo;
	@Column(name = "address")
	private String address;
	private String image;
	
	
	
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "updated_by")
	private String updatedBy;
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "updated_date")
	private LocalDateTime updatedDate;
	@Column(name = "active_status")
	private boolean active = true;
	

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

}
