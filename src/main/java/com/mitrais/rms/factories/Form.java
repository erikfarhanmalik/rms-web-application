package com.mitrais.rms.factories;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Form {
	private String action;
	private Map<String, FormControl> formControls;
}
