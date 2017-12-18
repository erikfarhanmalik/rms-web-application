package com.mitrais.rms.repositories;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mitrais.rms.models.Grade;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
	
	@Cacheable("findGradeByCode")
	public Grade findByCode(String code);
}
