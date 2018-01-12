package com.mitrais.rms.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Entity
@Data
public class Division {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "division_seq_gen")
	@SequenceGenerator(name = "division_seq_gen", sequenceName = "DIVISION_SEQ")
	private Integer id;
	private String code;
	private String name;
}
