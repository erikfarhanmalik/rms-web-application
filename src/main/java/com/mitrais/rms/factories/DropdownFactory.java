package com.mitrais.rms.factories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.mitrais.rms.services.DropdownService;
import com.mitrais.rms.util.Dropdown;

@Service
public class DropdownFactory {

	@Autowired
	private DropdownService dropdownService;

	public List<Dropdown> createDropdown(String type) {
		List<Dropdown> dropdown = Lists.newArrayList();
		switch (type) {
		case "division":
			dropdown = dropdownService.getDivisionDropdown();
			break;
		case "grade":
			dropdown = dropdownService.getGradeDropdown();
			break;
		case "gender":
			dropdown = dropdownService.getGenderDropdown();
			break;
		case "maritalStatus":
			dropdown = dropdownService.getMaritalStatusDropdown();
			break;
		case "status":
			dropdown = dropdownService.getEmployeeStatusDropdown();
			break;
		}
		return dropdown;
	}

	public List<Dropdown> createDropdown() {
		return dropdownService.getDivisionDropdown();
	}
}
