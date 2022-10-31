package com.demo.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.dtos.VisitDTO;
import com.demo.spring.entity.Visit;
import com.demo.spring.exceptions.OwnerNotFoundException;
import com.demo.spring.exceptions.PetNotFoundException;
import com.demo.spring.exceptions.PetNotRegWithOwnerException;
import com.demo.spring.exceptions.SplNotFoundException;
import com.demo.spring.service.VisitServiceImpl;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;

@RestController
@CrossOrigin
@RequestMapping("visit")
public class VisitRestController {
	
	@Autowired
	VisitServiceImpl visitService;
	
	@GetMapping(path = "/",produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.visit.listAll")
	public ResponseEntity<List<Visit>> listAll(){
		return ResponseEntity.ok(visitService.listAllVisits());
	}
	
	@GetMapping(path = "/{pid}",produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.visit.listAllByPID")
	public ResponseEntity<List<Visit>> listAllByPId(@PathVariable("pid") int id){
		return ResponseEntity.ok(visitService.listVisitById(id));
	}
	
	@GetMapping(path = "/pets/{ids}",produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.visit.listAllByPIDs")
	public ResponseEntity<List<Visit>> listAllByPIds(@PathVariable("ids") String ids){
		return ResponseEntity.ok(visitService.listVisitByPIds(ids));
	}
	
	@GetMapping(path = "/owner/{oid}",produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.visit.listAllByOID")
	public ResponseEntity<List<Visit>> listAllByOId(@PathVariable("oid") int id){
		return ResponseEntity.ok(visitService.listVisitByOId(id));
	}
	
	@PostMapping(path = "/",consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.visit.SAVE")
	public ResponseEntity<Message> saveVisit(@RequestBody Visit visit){
		return ResponseEntity.ok(visitService.recordVisit(visit));
	}
	
	@ExceptionHandler(OwnerNotFoundException.class)
    public ResponseEntity<Message> handleUserNotFoundException(OwnerNotFoundException une) {
        return ResponseEntity.ok(new Message("Owner not registered for this Owner Id"));
	}
	
	@ExceptionHandler(PetNotFoundException.class)
    public ResponseEntity<Message> handleUserNotFoundException(PetNotFoundException une) {
        return ResponseEntity.ok(new Message("Pet not found"));
	}
	
	@ExceptionHandler(SplNotFoundException.class)
    public ResponseEntity<Message> handleUserNotFoundException(SplNotFoundException une) {
        return ResponseEntity.ok(new Message("Speciality not registered"));
    }
	
	@ExceptionHandler(PetNotRegWithOwnerException.class)
    public ResponseEntity<Message> handleUserNotFoundException(PetNotRegWithOwnerException une) {
        return ResponseEntity.ok(new Message("pet not registered with this owner id"));
    }
	
	
	
	
}
