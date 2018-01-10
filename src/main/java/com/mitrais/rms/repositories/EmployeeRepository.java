package com.mitrais.rms.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;

import com.mitrais.rms.models.Employee;

public interface EmployeeRepository extends DataTablesRepository<Employee, Integer> {

	Employee findByUsername(String username);
	
	Page<Employee> findByFirstNameOrLastName(String firstName, String lastName, Pageable pageable);
	
	Page<Employee> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName, Pageable pageable);
	
	@Query("SELECT MAX(e.id) from Employee e") // MIN, AVG etc
    int getMaxId();
	
	@Query(value = "SELECT max(id) from Employee", nativeQuery = true)
    int getMaxIdWithNativeQuery();
}
