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
@Table(name = "professionaltax_table")
public class ProfessionalTax {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pt_id")
	private Long id;
	@Column(name = "ptin_sal")
	private Long ptinSal;
	@Column(name = "ptin_com")
	private String ptinCom;
	@Column(name = "tax_division")
	private String taxDivision;
	@Column(name = "tax_circle")
	private String taxCircle;
	@Column(name = "mobile_no")
	private Long mobileNo;
	@Column(name = "email")
	@Email
	private String email;
	@Column(name = "date_of_enrollment")
	private String dateOfEnrollment;
	@Column(name = "userid")
	private String userid;
	@Column(name = "password")
	private String password;
	@Column(name = "signature")
	private String signatory;

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