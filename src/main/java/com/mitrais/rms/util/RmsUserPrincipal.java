package com.mitrais.rms.util;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.collect.Lists;
import com.mitrais.rms.models.Employee;

public class RmsUserPrincipal implements UserDetails{

	private static final long serialVersionUID = -4077094744329510072L;
	private Employee employee;
	 
    public RmsUserPrincipal(Employee employee) {
        this.employee = employee;
    }
    
    public Employee getUser() {
    	return this.employee;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> roles = Lists.newArrayList(new SimpleGrantedAuthority(employee.getRole().name()));
		if("ROLE_ADMIN".equals(employee.getRole().name())) {
			roles.add(new SimpleGrantedAuthority("ROLE_ACTUATOR"));
		}
		return roles;
	}

	@Override
	public String getPassword() {
		return employee.getPassword();
	}

	@Override
	public String getUsername() {
		return employee.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
