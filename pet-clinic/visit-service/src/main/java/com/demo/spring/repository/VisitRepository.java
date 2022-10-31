package com.demo.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entity.Visit;

public interface VisitRepository extends JpaRepository<Visit, Integer> {

	@Query("select V from Visit V where V.petId=:petId")
	public List<Visit> getVisitByPid(int petId);
	
	@Query("select V from Visit V where V.ownerId=:ownerId")
	public List<Visit> getVisitByOid(int ownerId);
	
}
