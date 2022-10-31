package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "VET")
public class Vet {
	@Id
	@SequenceGenerator(sequenceName = "vet_sequence", initialValue = 50, name = "vet_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vet_id")
	@Column(name = "VETID")
	private int vetId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "PHNUMBER")
	private String phNumber;

	@Column(name = "SPLID")
	private int splId;

	public Vet() {

	}

	public Vet(String name, String phNumber, int splId) {
		this.name = name;
		this.phNumber = phNumber;
		this.splId = splId;
	}

	public int getVetId() {
		return vetId;
	}

	public void setVetId(int vetId) {
		this.vetId = vetId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhNumber() {
		return phNumber;
	}

	public void setPhNumber(String phNumber) {
		this.phNumber = phNumber;
	}

	public int getSplId() {
		return splId;
	}

	public void setSplId(int splId) {
		this.splId = splId;
	}

}
