package com.mitrais.rms.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mitrais.rms.annotations.EmailForm;
import com.mitrais.rms.annotations.NonForm;
import com.mitrais.rms.enums.EmployeeStatus;
import com.mitrais.rms.enums.Gender;
import com.mitrais.rms.enums.MaritalStatus;
import com.mitrais.rms.enums.UserRole;

import lombok.Data;

@Entity
@Data
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "employee_seq_gen")
	@SequenceGenerator(name = "employee_seq_gen", sequenceName = "EMPLOYEE_SEQ")
	@NonForm
	private Integer id;
	private String firstName;
	private String lastName;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@JsonFormat(pattern = "d MMMM, yyyy")
	private LocalDate dateOfBirth;
	private String nationality;

	@Enumerated(EnumType.STRING)
	private MaritalStatus maritalStatus;
	private String phone;
	private String subDivision;

	@Enumerated(EnumType.STRING)
	private EmployeeStatus status;

	@JsonFormat(pattern = "d MMMM, yyyy")
	private LocalDate suspendDate;
	@JsonFormat(pattern = "d MMMM, yyyy")
	private LocalDate hiredDate;

	@ManyToOne
	private Grade grade;

	@ManyToOne
	private Division division;
	
	@EmailForm
	private String email;
	private String location;

	@Column(nullable = false, unique = true)
	@NotNull
	@Size(max = 255)
	private String username;
	private String password;

	@Enumerated(EnumType.STRING)
	private UserRole role;
}
