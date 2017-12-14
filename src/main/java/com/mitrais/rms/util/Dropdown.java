package com.mitrais.rms.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dropdown {
	private Object value;
	private String label;

	public Dropdown(Object value, String label) {
		this.value = value;
		this.label = label;
	}
}
