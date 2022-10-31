package com.demo.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Pet;
import com.demo.spring.entity.PetDTO;
import com.demo.spring.exceptions.OwnerNotFoundException;
import com.demo.spring.exceptions.PetNotFoundException;
import com.demo.spring.services.PetServiceImpl;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;

@RestController
@CrossOrigin
@RequestMapping("owner")
public class PetRestController {

	@Autowired
	PetServiceImpl petService;
	
	/**
	 * Controller method calling service method To save a pet
	 * @param pet
	 * @return ResponseEntity<Message>
	 */
	@PostMapping(path = "/pet",consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.pet.save")
	public ResponseEntity<Message> savePet(@RequestBody PetDTO pet){
		Pet pPet= new Pet(pet.getName(),pet.getPetType(),pet.getOwnerId());
		return ResponseEntity.ok(petService.addPet(pPet));
	}
	
	/**
	 * Controller method calling service method To delete a pet by its pet id.
	 * @param id
	 * @return ResponseEntity<Message>
	 */
	@DeleteMapping(path = "/pet/{pid}")
	@Timed(value="request.pet.delete")
	public ResponseEntity<Message> deletePet(@PathVariable("pid") int id){
		return ResponseEntity.ok(petService.removePet(id));
	}
	
	/**
	 *  Controller method calling service method To get a list of all pets
	 * @return List of all pets
	 */
	@GetMapping(path = "/pet",produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.pet.listAll")
	public ResponseEntity<List<Pet>> listPets(){
		return ResponseEntity.ok(petService.listAllPets());
	}
	
	/**
	 * Controller method calling service method To find a pet by its pet id
	 * @param id
	 * @return pet object
	 */
	@GetMapping(path = "/pet/{pid}",produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.pet.find")
	public ResponseEntity<Pet> findPet(@PathVariable("pid") int id){
		return ResponseEntity.ok(petService.findPet(id));
	}
	
	/**
	 * Controller method calling service method To get a list of pets by its Owner Id
	 * @param id
	 * @return List of pet
	 */
	@GetMapping(path = "/pets/{oid}",produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.pet.listByOId")
	public ResponseEntity<List<Pet>> findPetByOid(@PathVariable("oid") int id){
		return ResponseEntity.ok(petService.getPetByOwnerId(id));
	}
	
	
	/**
	 * Handles exception OwnerNotFoundException
	 * @param une
	 * @return ResponseEntity<Message>
	 */
	@ExceptionHandler(OwnerNotFoundException.class)
    public ResponseEntity<Message> handleUserNotFoundException(OwnerNotFoundException une) {
        return ResponseEntity.ok(new Message("Owner not registered for this Owner Id"));
	}
	
	/**
	 * Handles exception PetNotFoundException
	 * @param une
	 * @return ResponseEntity<Message>
	 */
	@ExceptionHandler(PetNotFoundException.class)
    public ResponseEntity<Message> handleUserNotFoundException(PetNotFoundException une) {
        return ResponseEntity.ok(new Message("Pet not found"));
	}
}
