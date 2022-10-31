package com.demo.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entity.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {
	
	@Query(value = "SELECT COUNT(*) FROM OWNER o WHERE  o.PHNUMBER =:phNumber",nativeQuery = true)
	public Integer checkOwner(String phNumber);
	
	
	
	
}
