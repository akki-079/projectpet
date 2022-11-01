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

import com.demo.spring.entity.Speciality;
import com.demo.spring.entity.SpecialityDTO;
import com.demo.spring.exceptions.SplExistsException;
import com.demo.spring.exceptions.SplNotFoundException;
import com.demo.spring.services.SpecialityServiceImpl;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;

@RestController
@CrossOrigin
@RequestMapping("vet")
public class SpecialityRestController {

	@Autowired
	SpecialityServiceImpl splService;
	
	@GetMapping(path = "/spl",produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.spl.listAll")
	public ResponseEntity<List<Speciality>> listAllSpl(){
		return ResponseEntity.ok(splService.listSpecialities());
	}
	
	@PostMapping(path = "/spl")
	@Timed(value="request.spl.save")
	public ResponseEntity<Message> saveSpl(@RequestBody SpecialityDTO spl){
		Speciality spl1 = new Speciality(spl.getSplName());
		return ResponseEntity.ok(splService.addSpl(spl1));
	}
	
	@DeleteMapping(path = "/spl/{splid}")
	@Timed(value="request.spl.delete")
	public ResponseEntity<Message> deleteSpl(@PathVariable("splid") int id){
		return ResponseEntity.ok(splService.removeSpl(id));
	}
	
	@GetMapping(path = "/spl/{splName}",produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.spl.find")
	public ResponseEntity<Speciality> listAllSpl(@PathVariable("splName") String name){
		return ResponseEntity.ok(splService.getSplByName(name));
	}
	
	@ExceptionHandler(SplNotFoundException.class)
    public ResponseEntity<Message> handleUserNotFoundException(SplNotFoundException une) {
        return ResponseEntity.ok(new Message("Speciality not registered"));
    }
	
	@ExceptionHandler(SplExistsException.class)
    public ResponseEntity<Message> handleUserNotFoundException(SplExistsException une) {
        return ResponseEntity.ok(new Message("Speciality already registered"));
    }
}
