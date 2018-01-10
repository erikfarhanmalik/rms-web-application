package com.mitrais.rms.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import com.google.common.collect.Lists;
import com.mitrais.rms.enums.EmployeeStatus;
import com.mitrais.rms.enums.Gender;
import com.mitrais.rms.enums.MaritalStatus;
import com.mitrais.rms.enums.UserRole;
import com.mitrais.rms.models.Division;
import com.mitrais.rms.models.Employee;
import com.mitrais.rms.models.Grade;
import com.mitrais.rms.repositories.DivisionRepository;
import com.mitrais.rms.repositories.EmployeeRepository;
import com.mitrais.rms.repositories.GradeRepository;

@Controller
public class HomeController implements CommandLineRunner {

	private final EmployeeRepository employeeRepository;
	private final GradeRepository gradeRepository;
	private final DivisionRepository divisionRepository;
	private final Logger log = LoggerFactory.getLogger(HomeController.class);
	private String password = new BCryptPasswordEncoder().encode("password");

	@Autowired
	public HomeController(EmployeeRepository employeeRepository, GradeRepository gradeRepository,
			DivisionRepository divisionRepository) {
		this.employeeRepository = employeeRepository;
		this.gradeRepository = gradeRepository;
		this.divisionRepository = divisionRepository;
	}

	@GetMapping("/")
	public String homePage() {
		System.out.println("Employee max id: " + employeeRepository.getMaxId());
		System.out.println("Employee max id: " + employeeRepository.getMaxIdWithNativeQuery());
		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@Transactional
	public void generateInitialData() {
		generateGradeData();
		generateDivisionData();
		generateInitialUser();
	}

	private void generateInitialUser() {
		if (employeeRepository.count() == 0l) {
			log.info("Generating user data");

			createUserData("Erik", "Farhan Malik", "admin", "Bandung", "SE", "PG", "085603176696", UserRole.ROLE_ADMIN);
			createUserData("Sabari", "Insyafi", "employee", "Jakarta", "SE", "JP", "085623451232", UserRole.ROLE_EMPLOYEE);
			
			boolean needDumyData = false;
			if(needDumyData) {				
				populateRandomUser();
			}
			log.info("End generating user data");
		}
	}

	private void populateRandomUser() {
		List<String> firstNames = Lists.newArrayList("Keven", "Breanna", "Ivana", "Felicitas", "Tona", "Verena",
				"Bethanie", "Lyndsay", "Britany", "Roma", "Sharilyn", "Irene", "Domingo", "Natasha", "Chris", "Reyes",
				"Giuseppina", "Christal", "Andree", "Romana");

		List<String> lastNames = Lists.newArrayList("Aldo", "Shayna", "Earnestine", "Khalilah", "Classie", "Pinkie",
				"Eboni", "Lynetta", "Fonda", "Javier", "Ilene", "Noah", "Nga", "Paola", "Lupe", "Jay", "Lavera",
				"Leone", "Luke", "Ada");

		List<String> locations = Lists.newArrayList("Bandung", "Jakarta", "Bali", "Hanoi");

		List<String> divisionCodes = Lists.newArrayList("SE", "ADM", "CON");

		List<String> gradeCodes = Lists.newArrayList("JP", "PG", "AP", "AN", "CON-1", "ADM-1");

		Random rand = new Random();
		for (int i = 1; i <= 450; i++) {
			createUserData(
					firstNames.get(rand.nextInt(firstNames.size())),
					lastNames.get(rand.nextInt(lastNames.size())),
					"employee" + i,
					locations.get(rand.nextInt(locations.size())),
					divisionCodes.get(rand.nextInt(divisionCodes.size())),
					gradeCodes.get(rand.nextInt(gradeCodes.size())),
					"+6285" + Math.round(Math.random() * 1000) + Math.round(Math.random() * 1000),
					UserRole.ROLE_EMPLOYEE
					);
		}
	}

	private void createUserData(String firstName, String LastName, String username, String location,
			String divisionCode, String gradeCode, String phone, UserRole role) {
		Employee employee = new Employee();
		employee.setFirstName(firstName);
		employee.setLastName(LastName);
		employee.setGender(Gender.MALE);
		employee.setDateOfBirth(LocalDate.of(1989, 10, 20));
		employee.setNationality("Indonesia");
		employee.setMaritalStatus(MaritalStatus.MARRIED);
		employee.setPhone(phone);
		employee.setSubDivision("Java Desktop");
		employee.setStatus(EmployeeStatus.PERMANENT);
		employee.setHiredDate(LocalDate.of(2016, 11, 01));
		employee.setGrade(gradeRepository.findByCode(gradeCode));
		employee.setDivision(divisionRepository.findByCode(divisionCode));
		employee.setEmail(username + "@mitrais.com");
		employee.setLocation(location);
		employee.setUsername(username);
		employee.setPassword(password);
		employee.setRole(role);
		employeeRepository.save(employee);
	}

	private void generateDivisionData() {
		if (divisionRepository.count() == 0l) {
			log.info("Generating division data");

			Map<String, String> divisions = new HashMap<>();
			divisions.put("SE", "Software Engineer");
			divisions.put("CON", "Consultant");
			divisions.put("ADM", "Admin");

			divisions.forEach((key, value) -> {
				Division division = new Division();
				division.setCode(key);
				division.setName(value);
				divisionRepository.save(division);
			});

			log.info("End generating division data");
		}
	}
	
	private void generateGradeData() {
		if (gradeRepository.count() == 0l) {
			log.info("Generating grade data");

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
				gradeRepository.save(grade);
			});

			log.info("End generating grade data");
		}
	}

	@Override
	public void run(String... args) throws Exception {
		generateInitialData();
	}

}
