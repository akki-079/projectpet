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

import com.demo.spring.entity.Vet;
import com.demo.spring.entity.VetDTO;
import com.demo.spring.exceptions.SplNotFoundException;
import com.demo.spring.exceptions.VetExistsException;
import com.demo.spring.exceptions.VetNotFoundException;
import com.demo.spring.services.VetServiceImpl;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;

@RestController
@CrossOrigin
@RequestMapping("vet")
public class VetRestController {
	
	@Autowired
	VetServiceImpl vetService;
	
	@GetMapping(path = "/",produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.vet.listAll")
	public ResponseEntity<List<Vet>> listAll(){
		return ResponseEntity.ok(vetService.listAll());
	}
	
	@PostMapping(path = "/",consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.vet.save")
	public ResponseEntity<Message> saveVet(@RequestBody VetDTO vet){
		return ResponseEntity.ok(vetService.addVet(vet));
	}
	
	@GetMapping(path = "/{splName}",produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.vet.listVetBySpl")
	public ResponseEntity<List<Vet>> listVetBySpl(@PathVariable("splName") String splName){
		return ResponseEntity.ok(vetService.vetsForSpl(splName));
	}
	
	@GetMapping(path = "/id/{vid}",produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.vet.find")
	public ResponseEntity<Vet> vetById(@PathVariable("vid") int id){
		return ResponseEntity.ok(vetService.findVetById(id));
	}
	
	@DeleteMapping(path = "/{id}")
	@Timed(value="request.vet.delete")
	public ResponseEntity<Message> deleteVet(@PathVariable("id") int id){
		return ResponseEntity.ok(vetService.removeVet(id));
	}
	
	@PutMapping(path = "/{vid}/{ph}",consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.vet.update")
	public ResponseEntity<Message> editVet(@PathVariable("vid") int id,@PathVariable("ph") String ph,@RequestBody VetDTO vet){
		return ResponseEntity.ok(vetService.updateVet(id,ph,vet));
	}
	
	@ExceptionHandler(SplNotFoundException.class)
    public ResponseEntity<Message> handleUserNotFoundException(SplNotFoundException une) {
        return ResponseEntity.ok(new Message("Speciality not registered"));
    }
	
	@ExceptionHandler(VetExistsException.class)
    public ResponseEntity<Message> handleUserNotFoundException(VetExistsException une) {
        return ResponseEntity.ok(new Message("Phone number already registered"));
    }
	
	@ExceptionHandler(VetNotFoundException.class)
    public ResponseEntity<Message> handleUserNotFoundException(VetNotFoundException une) {
        return ResponseEntity.ok(new Message("Vet Not registered"));
    }
}
