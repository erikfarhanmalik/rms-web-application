package com.mitrais.rms.factories;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Form {
	private String action;
	private List<FormControl> formControls;
}
