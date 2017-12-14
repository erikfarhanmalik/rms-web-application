package com.mitrais.rms.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Division {
	@Id
	@GeneratedValue
	private Integer id;
	private String code;
	private String name;
}
