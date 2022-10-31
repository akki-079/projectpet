package com.demo.spring.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.spring.dtos.OwnerDTO;
import com.demo.spring.dtos.PetDTO;
import com.demo.spring.dtos.SpecialityDTO;
import com.demo.spring.entity.Visit;
import com.demo.spring.exceptions.OwnerNotFoundException;
import com.demo.spring.exceptions.PetNotFoundException;
import com.demo.spring.exceptions.PetNotRegWithOwnerException;
import com.demo.spring.exceptions.SplNotFoundException;
import com.demo.spring.repository.VisitRepository;
import com.demo.spring.util.Message;

@Service
public class VisitServiceImpl implements VisitService{

	private Logger LOGGER = LogManager.getLogger(this.getClass().getName());

	@Autowired
	VisitRepository visitRepository;

	@Autowired
	RestTemplate restTemplate;

	public List<Visit> listAllVisits() {
		LOGGER.info("List of visits retrieved successfully");
		return visitRepository.findAll();
	}

	public Message recordVisit(Visit visit) throws RuntimeException {
		int OId = visit.getOwnerId();
		int PId = visit.getPetId();
		String splName = visit.getSpeciality().toUpperCase();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> req = new HttpEntity<Void>(headers);
		ResponseEntity<OwnerDTO> res1 = restTemplate.exchange("http://owner-service/owner/" + OId, HttpMethod.GET, req,
				OwnerDTO.class);
		ResponseEntity<PetDTO> res2 = restTemplate.exchange("http://owner-service/owner/pet/" + PId, HttpMethod.GET,
				req, PetDTO.class);
		System.out.println(res1.getBody()+"res1");
		ResponseEntity<SpecialityDTO> res3 = restTemplate.exchange("http://vet-service/vet/spl/" + splName,
				HttpMethod.GET, req, SpecialityDTO.class);
		System.out.println(res1.getBody()+"res1");
		if (res1.getBody().getOwnerId() != OId) {
			LOGGER.error("Owner not found");
			throw new OwnerNotFoundException();
		} else {
			if (res2.getBody().getPetId() != PId) {
				LOGGER.error("Pet not found");
				throw new PetNotFoundException();
			} else {
				if(res2.getBody().getOwnerId() != OId) {
					LOGGER.error("Pet not registered with owner");
					throw new PetNotRegWithOwnerException();
				} else {
					if (res3.getBody().getSplName() == null || !res3.getBody().getSplName().equals(splName)) {
						LOGGER.error("Speciality not found");
						throw new SplNotFoundException();
					} else {
						visitRepository.save(visit);
						LOGGER.info("New visit recorded successfully");
						return new Message("Visit recorded successfully");
					}
				}
			}
		}

	}

	public List<Visit> listVisitById(int id) {
		LOGGER.info("List of visits retrieved successfully");
		return visitRepository.getVisitByPid(id);
	}

	public List<Visit> listVisitByOId(int id) {
		LOGGER.info("List of visits retrieved successfully");
		return visitRepository.getVisitByOid(id);
	}
	
	
	public List<Visit> listVisitByPIds(String  ids) {
		List<Visit> visitList = new ArrayList<>();
		List<String> pIds = Arrays.asList(ids.split(","));
		for(int i=0;i<pIds.size();i++) {
			int pId = Integer.parseInt(pIds.get(i));
			visitList.addAll(visitRepository.getVisitByPid(pId));
		}
		LOGGER.info("List of visits retrieved successfully");
		return visitList;
	}
}
