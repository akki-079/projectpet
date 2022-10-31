package com.demo.spring.dtos;

import java.util.List;

public class OwnerDTO {

	private int ownerId;

	private String name;

	private String city;

	private String phNumber;

	private List<PetDTO> petList;

	public OwnerDTO() {

	}

	public OwnerDTO(String name, String city, String phNumber) {

		this.name = name;
		this.city = city;
		this.phNumber = phNumber;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhNumber() {
		return phNumber;
	}

	public void setPhNumber(String phNumber) {
		this.phNumber = phNumber;
	}

	public List<PetDTO> getPetList() {
		return petList;
	}

	public void setPetList(List<PetDTO> petList) {
		this.petList = petList;
	}

}
