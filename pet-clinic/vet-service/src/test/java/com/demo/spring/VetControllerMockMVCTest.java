package com.demo.spring;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.spring.entity.Speciality;
import com.demo.spring.entity.Vet;
import com.demo.spring.repositories.SpecialityRepository;
import com.demo.spring.repositories.VetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class VetControllerMockMVCTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	SpecialityRepository splRepository;

	@MockBean
	VetRepository vetRepository;

	@Autowired
	TestRestTemplate template;

	@Test
	public void testFindAllSpl() throws Exception {
		Speciality spl = new Speciality("CARDIOLOGY");
		spl.setSplId(10);
		List<Speciality> splList = new ArrayList<>();
		splList.add(spl);
		when(splRepository.findAll()).thenReturn(splList);
		mvc.perform(get("/vet/spl")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json("[ {'splId':10,'splName':'CARDIOLOGY'}]"));
	}

	@Test
	public void testSaveSplSuccess() throws Exception {
		Speciality spl = new Speciality("CARDIOLOGY");
		spl.setSplId(10);
		ObjectMapper mapper = new ObjectMapper();
		String splJson = mapper.writeValueAsString(spl);
		when(splRepository.checkSpl(spl.getSplName())).thenReturn(0);
		mvc.perform(post("/vet/spl").content(splJson).contentType(MediaType.APPLICATION_JSON_VALUE))// consumes JSON
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Speciality added"));
	}

	@Test
	public void testSaveSplFailure() throws Exception {
		Speciality spl = new Speciality("CARDIOLOGY");
		spl.setSplId(10);
		ObjectMapper mapper = new ObjectMapper();
		String splJson = mapper.writeValueAsString(spl);
		when(splRepository.checkSpl(spl.getSplName())).thenReturn(1);
		mvc.perform(post("/vet/spl").content(splJson).contentType(MediaType.APPLICATION_JSON_VALUE))// consumes JSON
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Speciality already registered"));
	}

	@Test
	public void testDeleteSplByIdSuccess() throws Exception {
		Speciality spl = new Speciality("CARDIOLOGY");
		spl.setSplId(10);
		when(splRepository.findById(10)).thenReturn(Optional.of(spl));
		mvc.perform(delete("/vet/spl/10")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Speciality - " + spl.getSplName() + " has been deleted!!!"));
	}

	@Test
	public void testDeleteOwnerByIdFailure() throws Exception {
		Speciality spl = new Speciality("CARDIOLOGY");
		spl.setSplId(10);
		when(splRepository.findById(10)).thenReturn(Optional.empty());
		mvc.perform(delete("/vet/spl/10")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Speciality not registered"));
	}

	@Test
	public void testSaveVetExistsFailure() throws Exception {
		Vet vet = new Vet("Shalini", "9898989898", 10);
		vet.setVetId(100);
		ObjectMapper mapper = new ObjectMapper();
		String vetJson = mapper.writeValueAsString(vet);
		when(vetRepository.checkVet(vet.getPhNumber())).thenReturn(1);
		mvc.perform(post("/vet/").content(vetJson).contentType(MediaType.APPLICATION_JSON_VALUE))// consumes JSON
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Phone number already registered"));
	}

	@Test
	public void testSaveVetSplFailure() throws Exception {
		Vet vet = new Vet("Shalini", "9898989898", 10);
		vet.setVetId(100);
		ObjectMapper mapper = new ObjectMapper();
		String vetJson = mapper.writeValueAsString(vet);
		when(vetRepository.checkVet(vet.getPhNumber())).thenReturn(0);
		Speciality spl = new Speciality("CARDIOLOGY");
		spl.setSplId(10);
		when(splRepository.findById(10)).thenReturn(Optional.empty());
		mvc.perform(post("/vet/").content(vetJson).contentType(MediaType.APPLICATION_JSON_VALUE))// consumes JSON
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Speciality not registered"));
	}

	@Test
	public void testSaveVetSuccess() throws Exception {
		Vet vet = new Vet("Shalini", "9898989898", 10);
		vet.setVetId(100);
		ObjectMapper mapper = new ObjectMapper();
		String vetJson = mapper.writeValueAsString(vet);
		when(vetRepository.checkVet(vet.getPhNumber())).thenReturn(0);
		Speciality spl = new Speciality("CARDIOLOGY");
		spl.setSplId(10);
		when(splRepository.findById(10)).thenReturn(Optional.of(spl));
		mvc.perform(post("/vet/").content(vetJson).contentType(MediaType.APPLICATION_JSON_VALUE))// consumes JSON
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("New Vet " + vet.getName() + " successfully registered!!!"));
	}

	@Test
	public void testFindAllVet() throws Exception {
		Vet vet = new Vet("Shalini", "9898989898", 10);
		vet.setVetId(100);
		List<Vet> vetList = new ArrayList<>();
		vetList.add(vet);
		when(vetRepository.findAll()).thenReturn(vetList);
		mvc.perform(get("/vet/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json("[ {'vetId':100,'name':'Shalini','phNumber':'9898989898','splId':10}]"));
	}

	@Test
	public void testFindAllVetBySplFailure() throws Exception {
		Speciality spl = new Speciality("CARDIOLOGY");
		spl.setSplId(10);
		when(splRepository.checkSpl("CARDIOLOGY")).thenReturn(0);
		mvc.perform(get("/vet/CARDIOLOGY")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Speciality not registered"));
	}

	@Test
	public void testFindAllVetBySplSuccess() throws Exception {
		Vet vet = new Vet("Shalini", "9898989898", 10);
		vet.setVetId(100);
		List<Vet> vetList = new ArrayList<>();
		vetList.add(vet);
		Speciality spl = new Speciality("CARDIOLOGY");
		spl.setSplId(10);
		when(splRepository.checkSpl("CARDIOLOGY")).thenReturn(1);
		when(splRepository.getSplByName("CARDIOLOGY")).thenReturn(spl);
		when(vetRepository.vetBySpl(10)).thenReturn(vetList);
		mvc.perform(get("/vet/CARDIOLOGY")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json("[ {'vetId':100,'name':'Shalini','phNumber':'9898989898','splId':10}]"));
	}

	@Test
	public void testDeleteVetByIdSuccess() throws Exception {
		Vet vet = new Vet("Shalini", "9898989898", 10);
		vet.setVetId(100);
		when(vetRepository.findById(100)).thenReturn(Optional.of(vet));
		mvc.perform(delete("/vet/100")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Vet " + vet.getName() + " successfully deleted!!!"));
	}

	@Test
	public void testDeleteVetByIdFailure() throws Exception {
		Vet vet = new Vet("Shalini", "9898989898", 10);
		vet.setVetId(100);
		when(vetRepository.findById(100)).thenReturn(Optional.empty());
		mvc.perform(delete("/vet/100")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Vet Not registered"));
	}

	@Test
	public void testUpdateVetNotFoundFailure() throws Exception {
		Vet vet = new Vet("Shalini", "9898989898", 10);
		vet.setVetId(100);
		ObjectMapper mapper = new ObjectMapper();
		String vetJson = mapper.writeValueAsString(vet);
		when(vetRepository.findById(100)).thenReturn(Optional.empty());
		mvc.perform(put("/vet/100/9898989898").content(vetJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Vet Not registered"));
	}

	@Test
	public void testUpdateVetNumberExistsFailure() throws Exception {
		Vet vet = new Vet("Shalini", "9898989898", 10);
		vet.setVetId(100);
		ObjectMapper mapper = new ObjectMapper();
		String vetJson = mapper.writeValueAsString(vet);
		when(vetRepository.findById(100)).thenReturn(Optional.of(vet));
		when(vetRepository.checkVet("9898989898")).thenReturn(1);
		mvc.perform(put("/vet/100/9898989897").content(vetJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Phone number already registered"));
	}

	@Test
	public void testUpdateVetSuccessOne() throws Exception {
		Vet vet = new Vet("Shalini", "9898989898", 10);
		vet.setVetId(100);
		ObjectMapper mapper = new ObjectMapper();
		String vetJson = mapper.writeValueAsString(vet);
		when(vetRepository.findById(100)).thenReturn(Optional.of(vet));
		when(vetRepository.checkVet("9898989898")).thenReturn(0);
		mvc.perform(put("/vet/100/9898989897").content(vetJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Vet details updated successfully."));
	}

	@Test
	public void testUpdateVetSuccessTwo() throws Exception {
		Vet vet = new Vet("Shalini", "9898989898", 10);
		vet.setVetId(100);
		ObjectMapper mapper = new ObjectMapper();
		String vetJson = mapper.writeValueAsString(vet);
		when(vetRepository.findById(100)).thenReturn(Optional.of(vet));
		mvc.perform(put("/vet/100/9898989898").content(vetJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Vet details updated successfully."));
	}

	@Test
	public void testGetSplByNameFailure() throws Exception {
		Speciality spl = new Speciality("CARDIOLOGY");
		spl.setSplId(10);
		when(splRepository.checkSpl("CARDIOLOGY")).thenReturn(0);
		mvc.perform(get("/vet/spl/CARDIOLOGY")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Speciality not registered"));
	}

	@Test
	public void testGetSplByNameSuccess() throws Exception {
		Speciality spl = new Speciality("CARDIOLOGY");
		spl.setSplId(10);
		when(splRepository.checkSpl("CARDIOLOGY")).thenReturn(1);
		when(splRepository.getSplByName("CARDIOLOGY")).thenReturn(spl);
		mvc.perform(get("/vet/spl/CARDIOLOGY")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.splName").value("CARDIOLOGY"));
	}

	@Test
	public void testFindVetByIdSuccess() throws Exception {
		Vet vet = new Vet("Shalini", "9898989898", 10);
		vet.setVetId(100);
		when(vetRepository.findById(100)).thenReturn(Optional.of(vet));
		mvc.perform(get("/vet/id/100")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.name").value("Shalini"));
	}

	@Test
	public void testFindVetByIdFailure() throws Exception {
		Vet vet = new Vet("Shalini", "9898989898", 10);
		vet.setVetId(100);
		when(vetRepository.findById(100)).thenReturn(Optional.empty());
		mvc.perform(get("/vet/id/100")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Vet Not registered"));
	}

}
