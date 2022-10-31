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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Owner;
import com.demo.spring.entity.OwnerDTO;
import com.demo.spring.exceptions.OwnerExistsException;
import com.demo.spring.exceptions.OwnerNotFoundException;
import com.demo.spring.services.OwnerServiceImpl;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;

@RestController
@CrossOrigin
@RequestMapping("owner")
public class OwnerRestController {
	
	@Autowired
	OwnerServiceImpl ownerService;
	
	/**
	 * Controller method calling service method To return an owner object which has owner id as id
	 * @param id
	 * @return ResponseEntity<owner>
	 */
	@GetMapping(path="/{oid}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.owner.find")
	public ResponseEntity<Owner> findOwner(@PathVariable("oid") int id){
		return ResponseEntity.ok(ownerService.findOwner(id));
	}
	
	/**
	 * Controller method calling service method To return a list of all owners
	 * @return List of owners
	 */
	@GetMapping(path="/", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.owner.listAll")
	public ResponseEntity<List<Owner>> listAllOwner(){
		return ResponseEntity.ok(ownerService.listOwners());
	}
	
	/**
	 * Controller method calling service method To save an owner object
	 * @param owner
	 * @return ResponseEntity<Message> 
	 */
	@PostMapping(path="/",consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.owner.save")
	public ResponseEntity<Message> saveOwner(@RequestBody OwnerDTO owner){
		Owner pOwner = new Owner(owner.getName(),owner.getCity(),owner.getPhNumber());
		return ResponseEntity.ok(ownerService.addOwner(pOwner));
	}
	
	/**
	 * Controller method calling service method To delete an owner
	 * @param id
	 * @return ResponseEntity<Message>
	 */
	@DeleteMapping(path="/{oid}")
	@Timed(value="request.owner.delete")
	public ResponseEntity<Message> deleteOwner(@PathVariable("oid") int id){
		return ResponseEntity.ok(ownerService.removeOwner(id));
	}
	
	/**
	 * Controller method calling service method To update owner details
	 * @param id
	 * @param ph
	 * @param owner
	 * @return ResponseEntity<Message>
	 */
	@PutMapping(path = "/{oid}/{ph}",consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.owner.update")
	public ResponseEntity<Message> updateOwner(@PathVariable("oid") int id,@PathVariable("ph") String ph,@RequestBody OwnerDTO owner){
		Owner pOwner = new Owner(owner.getName(),owner.getCity(),owner.getPhNumber());
		return ResponseEntity.ok(ownerService.updateOwner(id,ph,pOwner));
	}
	
	/**
	 * Handles the exception OwnerNotFoundException
	 * @param une
	 * @return ResponseEntity<Message>
	 */
	@ExceptionHandler(OwnerNotFoundException.class)
    public ResponseEntity<Message> handleUserNotFoundException(OwnerNotFoundException une) {
        return ResponseEntity.ok(new Message("Owner not registered"));
    }
	
	/**
	 * Handles the exception OwnerExistsException
	 * @param une
	 * @return ResponseEntity<Message>
	 */
	@ExceptionHandler(OwnerExistsException.class)
    public ResponseEntity<Message> handleUserNotFoundException(OwnerExistsException une) {
        return ResponseEntity.ok(new Message("Phone number already registered"));
    }
	
	
}
