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

import com.demo.spring.entity.Owner;
import com.demo.spring.entity.Pet;
import com.demo.spring.repositories.OwnerRepository;
import com.demo.spring.repositories.PetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OwnerControllerMockMVCTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	OwnerRepository ownerRepository;

	@MockBean
	PetRepository petRepository;

	@Autowired
	TestRestTemplate template;

	@Test
	public void testFindOwnerByIdSuccess() throws Exception {
		Owner owner = new Owner("Kamar", "Allahabad", "9898989898");
		owner.setOwnerId(100);
		when(ownerRepository.findById(100)).thenReturn(Optional.of(owner));
		mvc.perform(get("/owner/100")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.ownerId").value("100"));
	}

	@Test
	public void testFindOwnerByIdFailure() throws Exception {
		Owner owner = new Owner("Kamar", "Allahabad", "9898989898");
		owner.setOwnerId(100);
		when(ownerRepository.findById(100)).thenReturn(Optional.empty());
		mvc.perform(get("/owner/100")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Owner not registered"));
	}

	@Test
	public void testFindAllOwner() throws Exception {
		Owner owner = new Owner("Kamar", "Allahabad", "9898989898");
		owner.setOwnerId(100);
		List<Owner> list = new ArrayList<>();
		list.add(owner);
		when(ownerRepository.findAll()).thenReturn(list);
		mvc.perform(get("/owner/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(
						content().json("[ {'ownerId':100,'name':'Kamar','city':'Allahabad','phNumber':'9898989898'}]"));

	}

	@Test
	public void testSaveOwnerSuccess() throws Exception {
		Owner owner = new Owner("Kamar", "Allahabad", "9898989898");
		owner.setOwnerId(100);
		ObjectMapper mapper = new ObjectMapper();
		String ownerJson = mapper.writeValueAsString(owner);
		when(ownerRepository.checkOwner("9898989898")).thenReturn(0);
		mvc.perform(post("/owner/").content(ownerJson).contentType(MediaType.APPLICATION_JSON_VALUE))// consumes JSON
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("New Owner created!!"));
	}

	@Test
	public void testSaveOwnerFailure() throws Exception {
		Owner owner = new Owner("Kamar", "Allahabad", "9898989898");
		owner.setOwnerId(100);
		ObjectMapper mapper = new ObjectMapper();// from jackson
		String ownerJson = mapper.writeValueAsString(owner);
		when(ownerRepository.checkOwner("9898989898")).thenReturn(1);
		mvc.perform(post("/owner/").content(ownerJson).contentType(MediaType.APPLICATION_JSON_VALUE))// consumes JSON
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Phone number already registered"));
	}

	@Test
	public void testDeleteOwnerByIdSuccess() throws Exception {
		Owner owner = new Owner("Kamar", "Allahabad", "9898989898");
		owner.setOwnerId(100);
		when(ownerRepository.findById(100)).thenReturn(Optional.of(owner));
		mvc.perform(delete("/owner/100")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Owner deleted from registry."));
	}

	@Test
	public void testDeleteOwnerByIdFailure() throws Exception {
		Owner owner = new Owner("Kamar", "Allahabad", "9898989898");
		owner.setOwnerId(100);
		when(ownerRepository.findById(100)).thenReturn(Optional.empty());
		mvc.perform(delete("/owner/100")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Owner not registered"));
	}

	@Test
	public void testUpdateOwnerNotFoundFailure() throws Exception {
		Owner owner = new Owner("Kamar", "Allahabad", "9898989898");
		owner.setOwnerId(100);
		ObjectMapper mapper = new ObjectMapper();
		String ownerJson = mapper.writeValueAsString(owner);
		when(ownerRepository.findById(100)).thenReturn(Optional.empty());
		mvc.perform(put("/owner/100/9898989898").content(ownerJson).contentType(MediaType.APPLICATION_JSON_VALUE))// consumes
																													// JSON
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Owner not registered"));
	}

	@Test
	public void testUpdateOwnerNumberExistsFailure() throws Exception {
		Owner owner = new Owner("Kamar", "Allahabad", "9898989898");
		owner.setOwnerId(100);
		ObjectMapper mapper = new ObjectMapper();
		String ownerJson = mapper.writeValueAsString(owner);
		when(ownerRepository.findById(100)).thenReturn(Optional.of(owner));
		when(ownerRepository.checkOwner("9898989898")).thenReturn(1);
		mvc.perform(put("/owner/100/9898989897").content(ownerJson).contentType(MediaType.APPLICATION_JSON_VALUE))// consumes
																													// JSON
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Phone number already registered"));
	}

	@Test
	public void testUpdateOwnerSuccessOne() throws Exception {
		Owner owner = new Owner("Kamar", "Allahabad", "9898989898");
		owner.setOwnerId(100);
		ObjectMapper mapper = new ObjectMapper();
		String ownerJson = mapper.writeValueAsString(owner);
		when(ownerRepository.findById(100)).thenReturn(Optional.of(owner));
		when(ownerRepository.checkOwner("9898989898")).thenReturn(0);
		mvc.perform(put("/owner/100/9898989897").content(ownerJson).contentType(MediaType.APPLICATION_JSON_VALUE))// consumes
																													// JSON
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Owner details updated successfully"));
	}

	@Test
	public void testUpdateOwnerSuccessTwo() throws Exception {
		Owner owner = new Owner("Kamar", "Allahabad", "9898989898");
		owner.setOwnerId(100);
		ObjectMapper mapper = new ObjectMapper();
		String ownerJson = mapper.writeValueAsString(owner);
		when(ownerRepository.findById(100)).thenReturn(Optional.of(owner));
		mvc.perform(put("/owner/100/9898989898").content(ownerJson).contentType(MediaType.APPLICATION_JSON_VALUE))// consumes
																													// JSON
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Owner details updated successfully"));
	}

	@Test
	public void testFindPetByIdSuccess() throws Exception {
		Pet pet = new Pet("Kuckoo", "Bird", 100);
		pet.setPetId(20);
		when(petRepository.findById(20)).thenReturn(Optional.of(pet));
		mvc.perform(get("/owner/pet/20")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.name").value("Kuckoo"));
	}

	@Test
	public void testFindPetByIdFailure() throws Exception {
		Pet pet = new Pet("Kuckoo", "Bird", 100);
		pet.setPetId(20);
		when(petRepository.findById(20)).thenReturn(Optional.empty());
		mvc.perform(get("/owner/pet/20")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Pet not found"));
	}

	@Test
	public void testDeletePetByIdSuccess() throws Exception {
		Pet pet = new Pet("Kuckoo", "Bird", 100);
		pet.setPetId(20);
		when(petRepository.findById(20)).thenReturn(Optional.of(pet));
		mvc.perform(delete("/owner/pet/20")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Pet removed from registry"));
	}

	@Test
	public void testDeletePetByIdFailure() throws Exception {
		Pet pet = new Pet("Kuckoo", "Bird", 100);
		pet.setPetId(20);
		when(petRepository.findById(20)).thenReturn(Optional.empty());
		mvc.perform(delete("/owner/pet/20")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Pet not found"));
	}

	@Test
	public void testSavePetSuccess() throws Exception {
		Pet pet = new Pet("Kuckoo", "Bird", 100);
		pet.setPetId(20);
		ObjectMapper mapper = new ObjectMapper();
		String petJson = mapper.writeValueAsString(pet);
		Owner owner = new Owner("Kamar", "Allahabad", "9898989898");
		owner.setOwnerId(100);
		when(ownerRepository.findById(100)).thenReturn(Optional.of(owner));
		mvc.perform(post("/owner/pet").content(petJson).contentType(MediaType.APPLICATION_JSON_VALUE))// consumes JSON
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("New pet added to owner with owner id : " + 100));
	}

	@Test
	public void testSavePetFailure() throws Exception {
		Pet pet = new Pet("Kuckoo", "Bird", 100);
		pet.setPetId(20);
		ObjectMapper mapper = new ObjectMapper();
		String petJson = mapper.writeValueAsString(pet);
		Owner owner = new Owner("Kamar", "Allahabad", "9898989898");
		owner.setOwnerId(100);
		when(ownerRepository.findById(100)).thenReturn(Optional.empty());
		mvc.perform(post("/owner/pet").content(petJson).contentType(MediaType.APPLICATION_JSON_VALUE))// consumes JSON
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Owner not registered for this Owner Id"));
	}

	@Test
	public void testFindAllPet() throws Exception {
		Pet pet = new Pet("Kuckoo", "Bird", 100);
		pet.setPetId(20);
		List<Pet> list = new ArrayList<>();
		list.add(pet);
		when(petRepository.findAll()).thenReturn(list);
		mvc.perform(get("/owner/pet")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json("[ {'petId':20,'name':'Kuckoo','petType':'Bird','ownerId':100}]"));

	}

	@Test
	public void testListPetByOwnerId() throws Exception{
		Pet pet = new Pet("Kuckoo", "Bird", 100);
		pet.setPetId(20);
		List<Pet> list = new ArrayList<>();
		list.add(pet);
		when(petRepository.getByOId(100)).thenReturn(list);
		mvc.perform(get("/owner/pets/100")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json("[ {'petId':20,'name':'Kuckoo','petType':'Bird','ownerId':100}]"));
	}
}
