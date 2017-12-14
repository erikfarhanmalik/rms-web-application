package com.mitrais.rms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mitrais.rms.models.Division;

public interface DivisionRepository extends JpaRepository<Division, Integer>{
	public Division findByCode(String code);
}
