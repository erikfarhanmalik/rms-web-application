package com.mitrais.rms.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mitrais.rms.models.Employee;
import com.mitrais.rms.repositories.EmployeeRepository;

@Controller
public class NaturalController {

	private final EmployeeRepository employeeRepository;

	@Autowired
	public NaturalController(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@GetMapping("/natural")
	public String index() {
		return "natural-index";
	}
	
	@GetMapping("/natural/api/employee/{id}")
	@ResponseBody
	public Employee getEmployee(@PathVariable("id") Integer id) {
		Employee employee = employeeRepository.findOne(id);
		return employee;
	}

	@GetMapping("/natural-second")
	public String second(Map<String, Object> model) {
		int page = 1;
		int pageSize = 5;
		Page<Employee> pageResult = employeeRepository.findAll(new PageRequest(page - 1, pageSize));
		model.put("employees", pageResult.getContent());
		
		return "natural-second";
	}
}
