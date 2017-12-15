package com.mitrais.rms.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mitrais.rms.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	Employee findByUsername(String username);
	
	Page<Employee> findByFirstNameOrLastName(String firstName, String lastName, Pageable pageable);
	
	Page<Employee> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName, Pageable pageable);
}
