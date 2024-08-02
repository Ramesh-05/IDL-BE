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
@Table(name = "mca_table")
public class MCA {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mca_id")
	private Long id;
	@Column(name = "cin")
	private String cin;
	@Column(name = "roc_name")
	private String rocName;
	@Column(name = "registration_name")
	private String registrationNo;
	@Column(name = "date_of_incorportion")
	private String dateOfIncorporation;
	@Column(name = "email_id")
	@Email
	private String email;
	@Column(name = "registered_address")
	private String registeredAddress;
	@Column(name = "stock_exchanges")
	private String stockExchanges;
	@Column(name = "class_of_company")
	private String classOfCompany;
	@Column(name = "authorised_capital")
	private String authorisedCapital;
	@Column(name = "paid_up_capital")
	private String paidUpCapital;
	@Column(name = "tele_phone")
	private String telephone;
	@Column(name = "mobile_no")
	private String mobileNo;
	@Column(name = "v2_login_id")
	private String v2loginid;
	@Column(name = "v3_login_id")
	private String v3loginid;
	@Column(name = "v2_email_id")
	@Email
	private String v2emailId;
	@Column(name = "v3_email_id")
	@Email
	private String v3emailId;
	@Column(name = "v2_password")
	private String v2password;
	@Column(name = "v3_password")
	private String v3password;
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
