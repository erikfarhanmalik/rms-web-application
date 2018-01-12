package com.mitrais.rms.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Entity
@Data
public class Grade {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "grade_seq_gen")
	@SequenceGenerator(name = "grade_seq_gen", sequenceName = "GRADE_SEQ")
	private Integer id;
	private String code;
	private String name;
}
