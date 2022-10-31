package com.demo.spring;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.spring.dtos.OwnerDTO;
import com.demo.spring.entity.Visit;
import com.demo.spring.repository.VisitRepository;
import com.demo.spring.util.Message;

import wiremock.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
		"ownerServer=http://localhost:${wiremock.server.port}", "vetServer=http://localhost:${wiremock.server.port}",
		"visitServer=http://localhost:${wiremock.server.port}" })

@AutoConfigureMockMvc
public class VisitControllerMockMVCTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	VisitRepository visitRepository;




	@Test
	public void testGetAllVisit() throws Exception {
		Visit visit = new Visit(10, 202, "itchiness", "18-10-22 1745 hrs", "DERMATOLOGY");
		visit.setVisitId(100);
		List<Visit> visitList = new ArrayList<>();
		visitList.add(visit);
		when(visitRepository.findAll()).thenReturn(visitList);
		mvc.perform(get("/visit/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json(
						"[{'visitId':100,'ownerId':10,'petId':202,'symptoms':'itchiness','dateTime':'18-10-22 1745 hrs','speciality':'DERMATOLOGY'}]"));
	}

	@Test
	public void testGetAllVisitByPid() throws Exception {
		Visit visit = new Visit(10, 202, "itchiness", "18-10-22 1745 hrs", "DERMATOLOGY");
		visit.setVisitId(100);
		List<Visit> visitList = new ArrayList<>();
		visitList.add(visit);
		when(visitRepository.getVisitByPid(202)).thenReturn(visitList);
		mvc.perform(get("/visit/202")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json(
						"[{'visitId':100,'ownerId':10,'petId':202,'symptoms':'itchiness','dateTime':'18-10-22 1745 hrs','speciality':'DERMATOLOGY'}]"));
	}

	@Test
	public void testGetAllVisitByOid() throws Exception {
		Visit visit = new Visit(10, 202, "itchiness", "18-10-22 1745 hrs", "DERMATOLOGY");
		visit.setVisitId(100);
		List<Visit> visitList = new ArrayList<>();
		visitList.add(visit);
		when(visitRepository.getVisitByOid(10)).thenReturn(visitList);
		mvc.perform(get("/visit/owner/10")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json(
						"[{'visitId':100,'ownerId':10,'petId':202,'symptoms':'itchiness','dateTime':'18-10-22 1745 hrs','speciality':'DERMATOLOGY'}]"));
	}

	@Test
	public void testGetAllVisitByPIds() throws Exception {
		Visit visit1 = new Visit(10, 202, "itchiness", "18-10-22 1745 hrs", "DERMATOLOGY");
		visit1.setVisitId(100);
		List<Visit> visitList1 = new ArrayList<>();
		visitList1.add(visit1);
		when(visitRepository.getVisitByPid(10)).thenReturn(visitList1);
		mvc.perform(get("/visit/pets/10,11")).andDo(print()).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json(
				"[{'visitId':100,'ownerId':10,'petId':202,'symptoms':'itchiness','dateTime':'18-10-22 1745 hrs','speciality':'DERMATOLOGY'}]"));
	}
	

}