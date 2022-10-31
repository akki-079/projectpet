package com.demo.spring.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entity.Speciality;

public interface SpecialityRepository extends JpaRepository<Speciality, Integer> {
	@Query(value = "SELECT COUNT(*) FROM SPECIALITY s WHERE s.SPLNAME =:splName", nativeQuery = true)
	public int checkSpl(String splName);
	
	@Query(value = "SELECT * FROM SPECIALITY s WHERE s.SPLNAME =:splName", nativeQuery = true)
	public Speciality getSplByName(String splName);
}
