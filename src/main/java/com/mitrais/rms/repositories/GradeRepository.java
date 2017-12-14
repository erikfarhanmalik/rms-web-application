package com.mitrais.rms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mitrais.rms.models.Grade;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
	public Grade findByCode(String code);
}
