package com.mitrais.rms.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mitrais.rms.enums.EmployeeStatus;
import com.mitrais.rms.enums.Gender;
import com.mitrais.rms.enums.MaritalStatus;
import com.mitrais.rms.enums.UserRole;

import lombok.Data;

@Entity
@Data
public class Employee {
	@Id
	@GeneratedValue
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
	private String email;
	private String location;
	
	@Column(nullable = false, unique = true)
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
	private UserRole role;
}
