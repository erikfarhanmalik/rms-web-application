package com.mitrais.rms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mitrais.rms.models.Employee;
import com.mitrais.rms.repositories.EmployeeRepository;
import com.mitrais.rms.util.RmsUserPrincipal;

@Service
public class RmsUserDetailsService implements UserDetailsService{
	private EmployeeRepository employeeRepository;
	
	@Autowired
    public RmsUserDetailsService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
    public UserDetails loadUserByUsername(String username) {
        Employee employee = employeeRepository.findByUsername(username);
        if (employee == null) {
            throw new UsernameNotFoundException(username);
        }
        return new RmsUserPrincipal(employee);
    }
}
