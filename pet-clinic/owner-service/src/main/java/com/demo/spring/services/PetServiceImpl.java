package com.demo.spring.services;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.entity.Owner;
import com.demo.spring.entity.Pet;
import com.demo.spring.exceptions.OwnerNotFoundException;
import com.demo.spring.exceptions.PetNotFoundException;
import com.demo.spring.repositories.OwnerRepository;
import com.demo.spring.repositories.PetRepository;
import com.demo.spring.util.Message;


@Service
public class PetServiceImpl implements PetService{
	
	private Logger LOGGER = LogManager.getLogger(this.getClass().getName());
	
	@Autowired
	PetRepository petRepository;
	
	
	@Autowired
	OwnerRepository ownerRepository;
	
	@Override
	public Pet findPet(int id) throws PetNotFoundException{
		Optional<Pet> opPet = petRepository.findById(id);
		if(opPet.isPresent()) {
			LOGGER.info("Pet found successfully");
			return opPet.get();
		} else {
			LOGGER.error("Pet not found");
			throw new PetNotFoundException();
		}
	}
	
	@Override
	public List<Pet> listAllPets(){
		LOGGER.info("Pet List retrieved successfully");
		return petRepository.findAll();
	}
	
	@Override
	public Message addPet(Pet pet) throws OwnerNotFoundException {
		Optional<Owner> opOwner = ownerRepository.findById(pet.getOwnerId());
		if(opOwner.isPresent()) {
			petRepository.save(pet);
			LOGGER.info("Pet added successfully");
			return new Message("New pet added to owner with owner id : "+pet.getOwnerId());
		} else {
			LOGGER.error("Pet not found");
			throw new OwnerNotFoundException();
		}
		
		
	}
	
	@Override
	public Message removePet(int id) throws PetNotFoundException {
		Optional<Pet> opPet = petRepository.findById(id);
		if(opPet.isPresent()) {
			petRepository.deleteById(id);
			LOGGER.info("Pet deleted successfully");
			return new Message("Pet removed from registry");
		} else {
			LOGGER.error("Pet not found");
			throw new PetNotFoundException();
		}
		
	}

	@Override
	public List<Pet> getPetByOwnerId(int ownerId) {
		LOGGER.info("Pet List retrieved successfully");
		return petRepository.getByOId(ownerId);
	}
	
}
