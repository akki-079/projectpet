package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "VISIT")
public class Visit {
	@Id
	@SequenceGenerator(sequenceName = "visit_sequence", initialValue = 20, name = "visit_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "visit_id")
	@Column(name = "VID")
	private int visitId;

	@Column(name = "OID")
	private int ownerId;

	@Column(name = "PID")
	private int petId;

	@Column(name = "SYMPTOMS")
	private String symptoms;

	@Column(name = "DATE_TIME")
	private String dateTime;

	@Column(name = "SPECIALITY")
	private String speciality;

	public Visit() {

	}

	public Visit(int ownerId, int petId, String symptoms, String dateTime, String speciality) {
		this.ownerId = ownerId;
		this.petId = petId;
		this.symptoms = symptoms;
		this.dateTime = dateTime;
		this.speciality = speciality;
	}

	public int getVisitId() {
		return visitId;
	}

	public void setVisitId(int visitId) {
		this.visitId = visitId;
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
