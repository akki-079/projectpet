package com.demo.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entity.Vet;

public interface VetRepository extends JpaRepository<Vet, Integer> {
	
	@Query(value = "SELECT COUNT(*) FROM VET v WHERE v.PHNUMBER =:phNumber",nativeQuery = true)
	public Integer checkVet(String phNumber);
	
	@Query("select v from Vet v where v.splId=:splId")
	public List<Vet> vetBySpl(int splId);
	
	@Query(value = "DELETE FROM VET WHERE SPLID =:splId",nativeQuery = true)
	public void deleteVetBySpl(int splId);
}
