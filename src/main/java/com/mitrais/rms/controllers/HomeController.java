package com.mitrais.rms.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.google.common.collect.Lists;
import com.mitrais.rms.enums.EmployeeStatus;
import com.mitrais.rms.enums.Gender;
import com.mitrais.rms.enums.MaritalStatus;
import com.mitrais.rms.models.Division;
import com.mitrais.rms.models.Employee;
import com.mitrais.rms.models.Grade;
import com.mitrais.rms.repositories.DivisionRepository;
import com.mitrais.rms.repositories.EmployeeRepository;
import com.mitrais.rms.repositories.GradeRepository;

@Controller
public class HomeController {
	
	private final EmployeeRepository employeeRepository;
	private final GradeRepository gradeRepository;
	private final DivisionRepository divisionRepository;
	
	@Autowired
	public HomeController(
			EmployeeRepository employeeRepository, 
			GradeRepository gradeRepository,
			DivisionRepository divisionRepository) {
		this.employeeRepository = employeeRepository;
		this.gradeRepository = gradeRepository;
		this.divisionRepository = divisionRepository;
	}

	@GetMapping("/")
	public String homePage() {
		generateInitialData();
		return "index";
	}

	private void generateInitialData() {
		List<Grade> gradeList = Lists.newArrayList();
		List<Division> divisionList = Lists.newArrayList();
		
		if(gradeRepository.count() == 0l) {
			Map<String, String> grades = new HashMap<>();
			grades.put("JP", "Junior Programmer");
			grades.put("PG", "Programmer");
			grades.put("AP", "Analist Programmer");
			grades.put("AN", "Analist");
			grades.put("CON-1", "Consultant 1");
			grades.put("ADM-1", "Admin 1");
			
			grades.forEach((key, value) -> {
				Grade grade = new Grade();
				grade.setCode(key);
				grade.setName(value);
				gradeList.add(gradeRepository.save(grade));
			});
		}
		
		if(divisionRepository.count() == 0l) {
			Map<String, String> divisions = new HashMap<>();
			divisions.put("SE", "Software Engineer");
			divisions.put("CON", "Consultant");
			divisions.put("ADM", "Admin");
			
			divisions.forEach((key, value) -> {
				Division division = new Division();
				division.setCode(key);
				division.setName(value);
				divisionList.add(divisionRepository.save(division));
			});
		}
		
		if(employeeRepository.count() == 0l) {
			Employee employee = new Employee();
			employee.setFirstName("Erik");
			employee.setLastName("Farhan Malik");
			employee.setGender(Gender.MALE);
			employee.setDateOfBirth(LocalDate.of(1989, 10, 20));
			employee.setNationality("Indonesia");
			employee.setMaritalStatus(MaritalStatus.MARRIED);
			employee.setPhone("085603176696");
			employee.setSubDivision("Java Web");
			employee.setStatus(EmployeeStatus.PERMANENT);
			employee.setHiredDate(LocalDate.of(2016, 11, 01));			
			employee.setGrade(gradeList.stream().filter(grade -> "PG".equals(grade.getCode())).findFirst().orElse(null));
			employee.setDivision(divisionList.stream().filter(division -> "SE".equals(division.getCode())).findFirst().orElse(null));
			employee.setEmail("erikfarhan.malik@mitrais.com");
			employee.setLocation("Bandung");
			employeeRepository.save(employee);
		}
	}

	@GetMapping("/login")
	public String login() {
		return "/login";
	}
}
