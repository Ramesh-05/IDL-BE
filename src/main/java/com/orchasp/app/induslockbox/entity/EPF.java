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
@Table(name = "epf_table")
public class EPF {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "epf_id")
	private Long id;
	@Column(name = "est_id")
	private String estid;
	@Column(name = "lin")
	private Long lin;
	@Column(name = "mobile_no")
	private String mobileNo;
	@Column(name = "email_id")
	@Email
	private String emailId;
	@Column(name = "pan_no")
	private String panno;
	@Column(name = "nic_code")
	private String niccode;
	@Column(name = "pf_office_address")
	private String pfOfficeAddress;
	@Column(name = "user_id")
	private String userid;
	@Column(name = "password")
	private String password;

	@Column(name = "signatory")
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
