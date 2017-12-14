package com.mitrais.rms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mitrais.rms.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	Employee findByUsername(String username);
}
