package com.mitrais.rms.factories;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormControl {
	private String name;
	private String label;
	private String type;
	private boolean isRequired;
	private Integer length;
}
