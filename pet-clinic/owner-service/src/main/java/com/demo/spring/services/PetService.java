package com.demo.spring.services;

import java.util.List;


import com.demo.spring.entity.Pet;
import com.demo.spring.util.Message;

public interface PetService {
	
	/**
	 * To find pet by pet id
	 * @param id
	 * @return Pet
	 */
	public Pet findPet(int id);
	/**
	 * To list all pets
	 * @return List of pet
	 */
	public List<Pet> listAllPets();
	/**
	 * To add pet
	 * @param pet
	 * @return Message
	 */
	public Message addPet(Pet pet);
	/**
	 * To remove pet
	 * @param id
	 * @return Message
	 */
	public Message removePet(int id);
	/**
	 * To list pets by owner id
	 * @param ownerId
	 * @return List of pet
	 */
	public List<Pet> getPetByOwnerId(int ownerId);
}
