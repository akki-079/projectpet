package com.demo.spring.dtos;

public class PetDTO {

	private int petId;

	private String name;

	private String petType;

	private int ownerId;

	public PetDTO() {

	}

	public PetDTO(String name, String petType, int ownerId) {

		this.name = name;
		this.petType = petType;
		this.ownerId = ownerId;
	}

	public int getPetId() {
		return petId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPetType() {
		return petType;
	}

	public void setPetType(String petType) {
		this.petType = petType;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

}
