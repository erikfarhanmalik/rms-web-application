package com.mitrais.rms.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitrais.rms.factories.Form;
import com.mitrais.rms.factories.FormFactory;
import com.mitrais.rms.models.Employee;
import com.mitrais.rms.repositories.EmployeeRepository;

@Controller
public class EmployeeCrudController {
	private final EmployeeRepository employeeRepository;

	@Autowired
	public EmployeeCrudController(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@GetMapping(value = "/employee-crud")
	public String divsionPage() throws Exception {
		Form form = FormFactory.createEmployeeForm();
		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println(objectMapper.writeValueAsString(form));
		return "employee-crud";
	}

	@GetMapping(value = "/rms-api/employee")
	@ResponseBody
	public DataTablesOutput<Employee> getUsers(@Valid DataTablesInput input) {
		return employeeRepository.findAll(input);
	}

}
