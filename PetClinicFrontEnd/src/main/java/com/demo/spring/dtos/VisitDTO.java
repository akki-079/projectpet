package com.demo.spring.dtos;

public class VisitDTO {

	private int visitId;

	private int ownerId;

	private int petId;

	private String symptoms;

	private String dateTime;

	private String speciality;

	public VisitDTO() {

	}

	public VisitDTO(int ownerId, int petId, String symptoms, String dateTime, String speciality) {
		this.ownerId = ownerId;
		this.petId = petId;
		this.symptoms = symptoms;
		this.dateTime = dateTime;
		this.speciality = speciality;
	}

	public int getVisitId() {
		return visitId;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public String getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

}
