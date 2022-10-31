package com.demo.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Integer> {
	
	@Query(value = "DELETE FROM PET WHERE OID =:oid",nativeQuery = true)
	public Integer deletePetByOId(int oid);
	
	@Query(value = "SELECT * FROM PET p WHERE  p.OID =:oid",nativeQuery = true)
	public List<Pet> getByOId(int oid);
}
