package com.mitrais.rms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mitrais.rms.models.Employee;
import com.mitrais.rms.repositories.EmployeeRepository;

@Controller
public class DandelionController {
	private final EmployeeRepository employeeRepository;

	@Autowired
	public DandelionController(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@GetMapping("/dandelion")
	public String dandelionIndex() {
		return "dandelion";
	}
	
	@GetMapping("/dandelion-ajax")
	public String dandelionAjax() {
		return "dandelion-ajax";
	}
	
	
	@GetMapping(value = "/dandelion-api/employees", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEmployeeList() {
		ResponseEntity<Object> result;
		try {
			ObjectMapper mapper = new ObjectMapper();
			PageRequest pageRequest = new PageRequest(0, 5);
			Page<Employee> data = employeeRepository.findAll(pageRequest);
			JsonNode responseData = mapper.createObjectNode().set("data", mapper.valueToTree(data.getContent()));
			result = ResponseEntity.ok(responseData);
		} catch (Exception e) {
			ObjectNode responseBody = new ObjectMapper().createObjectNode();
			responseBody.put("status", "failed");
			responseBody.put("reason", e.getMessage());
			result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
		}

		return result;
	}
	

	
}
