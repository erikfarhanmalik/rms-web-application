package com.mitrais.rms.repositories;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mitrais.rms.models.Division;

public interface DivisionRepository extends JpaRepository<Division, Integer>{
	
	@Cacheable("findDivisionByCode")
	public Division findByCode(String code);
}
