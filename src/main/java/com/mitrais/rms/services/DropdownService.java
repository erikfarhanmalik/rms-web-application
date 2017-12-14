package com.mitrais.rms.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.mitrais.rms.enums.EmployeeStatus;
import com.mitrais.rms.enums.Gender;
import com.mitrais.rms.enums.MaritalStatus;
import com.mitrais.rms.repositories.DivisionRepository;
import com.mitrais.rms.repositories.GradeRepository;
import com.mitrais.rms.util.Dropdown;

@Component
public class DropdownService {

	private GradeRepository gradeRepository;
	private DivisionRepository divisionRepository;

	@Autowired
	public DropdownService(GradeRepository gradeRepository, DivisionRepository divisionRepository) {
		this.gradeRepository = gradeRepository;
		this.divisionRepository = divisionRepository;
	}

	public List<Dropdown> getGradeDropdown() {
		List<Dropdown> dropdowns = Lists.newArrayList();
		gradeRepository.findAll().forEach(grade -> dropdowns.add(new Dropdown(grade.getId(), grade.getName())));
		return dropdowns;
	}

	public List<Dropdown> getDivisionDropdown() {
		List<Dropdown> dropdowns = Lists.newArrayList();
		divisionRepository.findAll().forEach(division -> dropdowns.add(new Dropdown(division.getId(), division.getName())));
		return dropdowns;
	}

	public List<Dropdown> getMaritalStatusDropdown() {
		List<Dropdown> dropdowns = Lists.newArrayList();
		Arrays.asList(MaritalStatus.values()).forEach(status -> dropdowns.add(new Dropdown(status.name(), status.name())));
		return dropdowns;
	}

	public List<Dropdown> getGenderDropdown() {
		List<Dropdown> dropdowns = Lists.newArrayList();
		Arrays.asList(Gender.values()).forEach(gender -> dropdowns.add(new Dropdown(gender.name(), gender.name())));
		return dropdowns;
	}

	public List<Dropdown> getEmployeeStatusDropdown() {
		List<Dropdown> dropdowns = Lists.newArrayList();
		Arrays.asList(EmployeeStatus.values()).forEach(status -> dropdowns.add(new Dropdown(status.name(), status.name())));
		return dropdowns;
	}
}
