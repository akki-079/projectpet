package com.demo.spring.services;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.entity.Speciality;
import com.demo.spring.entity.Vet;
import com.demo.spring.exceptions.SplNotFoundException;
import com.demo.spring.exceptions.VetExistsException;
import com.demo.spring.exceptions.VetNotFoundException;
import com.demo.spring.repositories.SpecialityRepository;
import com.demo.spring.repositories.VetRepository;
import com.demo.spring.util.Message;

@Service
public class VetServiceImpl implements VetService{
	
	private Logger LOGGER = LogManager.getLogger(this.getClass().getName());
	
	@Autowired
	VetRepository vetRepository;
	
	@Autowired
	SpecialityRepository splRepository;
	
	@Override
	public List<Vet> listAll(){
		LOGGER.info("Vet list retrieved successfully.");
		return vetRepository.findAll();
	}
	
	@Override
	public Message addVet(Vet vet) throws RuntimeException{
		if(vetRepository.checkVet(vet.getPhNumber())>0) {
			LOGGER.error("Vet already exists");
			throw new VetExistsException();
		} else {
			Optional<Speciality> opSpl = splRepository.findById(vet.getSplId());
			if(opSpl.isPresent()) {
				vetRepository.save(vet);
				LOGGER.info("New vet :"+vet.getName()+" successfully registered");
				return new Message("New Vet "+vet.getName()+" successfully registered!!!");
			} else {
				LOGGER.error("Invalid speciality provided");
				throw new SplNotFoundException();
			}
		}
	}
	
	@Override
	public Message removeVet(int id) throws VetNotFoundException{
		Optional<Vet> opVet = vetRepository.findById(id);
		if(opVet.isPresent()) {
			vetRepository.deleteById(id);
			LOGGER.info("Vet :"+opVet.get().getName()+" successfully deregistered");
			return new Message("Vet "+opVet.get().getName()+" successfully deleted!!!");
		} else {
			LOGGER.error("Vet not registered.");
			throw new VetNotFoundException();
		}
	}
	
	@Override
	public List<Vet> vetsForSpl(String splName) throws SplNotFoundException {
		splName = splName.toUpperCase();
		
		if(splRepository.checkSpl(splName) == 0) {
			LOGGER.error("Invalid speciality passed");
			throw new SplNotFoundException();
		} else {
			LOGGER.info("Vet list retrived successfully");
			return vetRepository.vetBySpl(splRepository.getSplByName(splName).getSplId());
		}
	}
	
	@Override
	public Message updateVet(int id,String phNumber,Vet vet) throws RuntimeException{
		vet.setVetId(id);
		Optional<Vet> opVet = vetRepository.findById(id);
		if(opVet.isPresent()) {
			if(phNumber.equals(vet.getPhNumber())) {
				vetRepository.save(vet);
				LOGGER.info("vet info updated");
				return new Message("Vet details updated successfully.");
			} else {
				if(vetRepository.checkVet(vet.getPhNumber())>0) {
					LOGGER.error("Vet already exists");
					throw new VetExistsException();
				} else {
					vetRepository.save(vet);
					LOGGER.info("vet info updated");
					return new Message("Vet details updated successfully.");
				}
			}
		} else {
			LOGGER.error("Vet not registered.");
			throw new VetNotFoundException();
		}
	}
	
	@Override
	public Vet findVetById(int id) throws VetNotFoundException{
		Optional<Vet> opVet = vetRepository.findById(id);
		if(opVet.isPresent()) {
			LOGGER.info("vet found");
			return opVet.get();
		} else {
			LOGGER.error("Vet not found");
			throw new VetNotFoundException();
		}
	}
}
