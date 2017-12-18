package com.mitrais.rms.services;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.mitrais.rms.controllers.HomeController;
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
	private final Logger log = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	public DropdownService(GradeRepository gradeRepository, DivisionRepository divisionRepository) {
		this.gradeRepository = gradeRepository;
		this.divisionRepository = divisionRepository;
	}

	public List<Dropdown> getGradeDropdown() {
		log.info("init get GradeDropdown");
		List<Dropdown> dropdowns = Lists.newArrayList();
		gradeRepository.findAll().forEach(grade -> dropdowns.add(new Dropdown(grade.getId(), grade.getName())));
		return dropdowns;
	}

	@Cacheable("divisionDropdown")
	public List<Dropdown> getDivisionDropdown() {
		log.info("init get DivisionDropdown");
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
