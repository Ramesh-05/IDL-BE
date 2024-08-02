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
@Table(name = "bank_table")
public class Bank {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bank_id")
	private Long id;
	@Column(name = "account_holder_name")
	private String accountHolderName;
	@Column(name = "account_number")
	private String bankAccountNumber;
	@Column(name = "ifsc_code")
	private String ifccode;
	@Column(name = "mirc_code")
	private String mcircode;
	@Column(name = "bank_name")
	private String bankName;
	@Column(name = "bank_branch")
	private String branch;
	@Column(name = "transaction_password")
	private String transpassword;
	@Column(name = "account_type")
	private String accountType;
	@Column(name = "email_id")
	@Email
	private String email;
	@Column(name = "mobile_no")
	private String mobileNo;
	@Column(name = "primary_signatory")
	private String primarysignatory;
	@Column(name = "secondary_signatory")
	private String secondarysignatory;
	@Column(name = "user_id")
	private String loginid;
	@Column(name = "login_password")
	private String loginpassword;

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
