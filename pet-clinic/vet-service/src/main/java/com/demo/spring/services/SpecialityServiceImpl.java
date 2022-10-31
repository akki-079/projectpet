package com.demo.spring.services;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.entity.Speciality;
import com.demo.spring.exceptions.SplExistsException;
import com.demo.spring.exceptions.SplNotFoundException;
import com.demo.spring.repositories.SpecialityRepository;
import com.demo.spring.repositories.VetRepository;
import com.demo.spring.util.Message;



@Service
public class SpecialityServiceImpl implements SpecialityService {
	
	private Logger LOGGER = LogManager.getLogger(this.getClass().getName());

	
	@Autowired
	SpecialityRepository specialityRepository;
	
	@Autowired
	VetRepository vetRepository;
	
	@Override
	public List<Speciality> listSpecialities(){
		LOGGER.info("Speciality list retrieved successfuly");
		return specialityRepository.findAll();
	}
	
	@Override
	public Message addSpl(Speciality spl) throws SplExistsException {
		spl.setSplName(spl.getSplName().toUpperCase());
		if(specialityRepository.checkSpl(spl.getSplName())==0) {
			specialityRepository.save(spl);
			LOGGER.info("Speciality : "+spl.getSplName()+" added");
			return new Message("Speciality added");
		} else {
			LOGGER.error("Speciality alreay exists");
			throw new SplExistsException();
		}
		
			
	}
	
	@Override
	public Message removeSpl(int id) throws SplNotFoundException {
		Optional<Speciality> opSpl = specialityRepository.findById(id);
		if(opSpl.isPresent()) {
			specialityRepository.deleteById(id);
			vetRepository.deleteVetBySpl(id);
			LOGGER.info("Speciality :"+opSpl.get().getSplName()+" deleted successfuly");
			return new Message("Speciality - "+opSpl.get().getSplName()+" has been deleted!!!");
		} else {
			LOGGER.error("Speciality not registered");
			throw new SplNotFoundException();
		}
	}
	
	@Override
	public Speciality getSplByName(String splName) throws SplNotFoundException {
		splName = splName.toUpperCase();
		if(specialityRepository.checkSpl(splName) == 0) {
			LOGGER.error("Speciality not found");
			throw new SplNotFoundException();
		} else {
			LOGGER.info("spl found successfully");
			return specialityRepository.getSplByName(splName);
		}
	}
}
