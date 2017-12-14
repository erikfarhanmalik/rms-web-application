package com.mitrais.rms.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mitrais.rms.models.Employee;
import com.mitrais.rms.repositories.DivisionRepository;
import com.mitrais.rms.repositories.EmployeeRepository;
import com.mitrais.rms.repositories.GradeRepository;
import com.mitrais.rms.services.DropdownService;

@Controller
public class EmployeeController {
	private final EmployeeRepository employeeRepository;
	private final GradeRepository gradeRepository;
	private final DivisionRepository divisionRepository;
	private final DropdownService dropdownService;
	private final SpringTemplateEngine templateEngine;

	@Autowired
	public EmployeeController(EmployeeRepository employeeRepository, GradeRepository gradeRepository,
			DivisionRepository divisionRepository, DropdownService dropdownService,
			SpringTemplateEngine templateEngine) {
		this.employeeRepository = employeeRepository;
		this.gradeRepository = gradeRepository;
		this.divisionRepository = divisionRepository;
		this.dropdownService = dropdownService;
		this.templateEngine = templateEngine;
	}

	@GetMapping("/employees")
	public String employeeList(Map<String, Object> model) {
		List<Employee> employees = employeeRepository.findAll();
		model.put("genderOptions", dropdownService.getGenderDropdown());
		model.put("employeeStatusOptions", dropdownService.getEmployeeStatusDropdown());
		model.put("maritalStatusOptions", dropdownService.getMaritalStatusDropdown());
		model.put("gradeOptions", dropdownService.getGradeDropdown());
		model.put("divisionOptions", dropdownService.getDivisionDropdown());
		model.put("employees", employees);

		return "employees";
	}

	@PostMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> saveEmployee(@RequestBody Employee employee) {
		ResponseEntity<Object> result;
		try {
			employee.setGrade(gradeRepository.findOne(employee.getGrade().getId()));
			employee.setDivision(divisionRepository.findOne(employee.getDivision().getId()));
			result = ResponseEntity.ok(employeeRepository.save(employee));
		} catch (Exception e) {
			ObjectNode responseBody = new ObjectMapper().createObjectNode();
			responseBody.put("status", "failed");
			responseBody.put("reason", e.getMessage());
			result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
		}

		return result;
	}

	@GetMapping(value = "/employees/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<Object> getEmployee(@PathVariable("id") Integer id) {
		ResponseEntity<Object> result;
		try {
			Employee employee = employeeRepository.findOne(id);
			Context context = new Context();
			context.setVariable("employee", employee);
			String content = templateEngine.process("detail-employee", context);
			result = ResponseEntity.ok(content);
		} catch (Exception e) {
			result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
		}

		return result;
	}
}
