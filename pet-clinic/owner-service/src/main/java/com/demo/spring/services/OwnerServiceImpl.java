package com.demo.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.entity.Owner;
import com.demo.spring.entity.Pet;
import com.demo.spring.exceptions.OwnerExistsException;
import com.demo.spring.exceptions.OwnerNotFoundException;
import com.demo.spring.repositories.OwnerRepository;
import com.demo.spring.repositories.PetRepository;
import com.demo.spring.util.Message;

/**
 * OwnerServiceImpl class which implements OwnerService Interface
 * @author aksbhand2
 *
 */
@Service
public class OwnerServiceImpl implements OwnerService {
	 
	private Logger LOGGER = LogManager.getLogger(this.getClass().getName());
	
	@Autowired
	OwnerRepository ownerRepository;
	
	@Autowired
	PetRepository petRepository;
	
	@Override
	public Owner findOwner(int id) throws OwnerNotFoundException{
		Optional<Owner> opOwner = ownerRepository.findById(id);
		if(opOwner.isPresent()) {
			LOGGER.info("owner found successfully");
			List<Pet> petList = petRepository.findAll();
			List<Pet> petForOwnerList = new ArrayList<>();
			for(int j = 0;j<petList.size();j++) {
				if(petList.get(j).getOwnerId() == id) {
					petForOwnerList.add(petList.get(j));
				}
			}
			opOwner.get().setPetList(petForOwnerList);
			
			return opOwner.get();
		} else {
			LOGGER.error("Owner with owner id:"+ id +" not available in directory");
			throw new OwnerNotFoundException();
		}
	}
	
	@Override
	public List<Owner> listOwners(){
		List<Owner> ownerList = ownerRepository.findAll();
		List<Pet> petList = petRepository.findAll();
		for(int i = 0;i<ownerList.size();i++) {
			List<Pet> petForOwnerList = new ArrayList<>();
			for(int j = 0;j<petList.size();j++) {
				if(petList.get(j).getOwnerId() == ownerList.get(i).getOwnerId()) {
					petForOwnerList.add(petList.get(j));
				}
			}
			ownerList.get(i).setPetList(petForOwnerList);
		}
		LOGGER.info("Owner list retrieved successfully");
		return ownerList;
	}
	
	@Override
	public Message addOwner(Owner owner) throws OwnerExistsException {
		if(ownerRepository.checkOwner(owner.getPhNumber())>0) {
			LOGGER.error("Owner already registered");
			throw new OwnerExistsException();
		} else {
			ownerRepository.save(owner);
			LOGGER.info("New owner created successfully");
			return new Message("New Owner created!!");
		}
	}
	
	@Override
	public Message removeOwner(int id) throws OwnerNotFoundException {
		Optional<Owner> opOwner = ownerRepository.findById(id);
		if(opOwner.isPresent()) {
			ownerRepository.deleteById(id);
			petRepository.deletePetByOId(id);
			LOGGER.info("Owner deleted from registry successfully");
			return new Message("Owner deleted from registry.");
		} else {
			LOGGER.error("Owner not found");
			throw new OwnerNotFoundException();
		}
	}
	
	@Override
	public Message updateOwner(int id,String ph,Owner owner) throws RuntimeException{
		owner.setOwnerId(id);
		Optional<Owner> opOwner = ownerRepository.findById(id);
		if(opOwner.isPresent()) {
			if(ph.equals(owner.getPhNumber())) {
				ownerRepository.save(owner);
				LOGGER.info("Owner updated");
				return new Message("Owner details updated successfully");
			} else {
				if(ownerRepository.checkOwner(owner.getPhNumber())>0) {
					LOGGER.error("Owner already registered");
					throw new OwnerExistsException();
				} else {
					ownerRepository.save(owner);
					LOGGER.info("Owner details updated successfully");
					return new Message("Owner details updated successfully");
				}
			}
			
		} else {
			LOGGER.error("owner not found");
			throw new OwnerNotFoundException();
		}
			
	}
	
	
	
	
}
