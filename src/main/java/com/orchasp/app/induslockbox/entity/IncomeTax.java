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
@Table(name = "incometax_table")
public class IncomeTax {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "incometax_id")
	private Long id;
	@Column(name = "pan_number")
	private String panNumber;
	@Column(name = "name")
	private String name;
	@Column(name = "issued_date(pan)")
	private String panIssuedDate;
	@Column(name = "incorporation_date")
	private String incorporationDate;
	@Column(name = "primary_mobile")
	private Long primaryMobile;
	@Column(name = "secondary_mobile")
	private Long secondaryMobile;
	@Email
	@Column(name = "primary_email")
	private String primaryEmail;
	@Email
	@Column(name = "secondary_email")
	private String secondaryEmail;
	@Column(name = "tds")
	private Double tds;
	@Column(name = "user_id")
	private String userid;
	@Column(name = "password")
	private String password;
	@Column(name = "primary_signatory")
	private String primarysignatory;
	@Column(name = "secondary_signatory")
	private String secondarysignatory;
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
