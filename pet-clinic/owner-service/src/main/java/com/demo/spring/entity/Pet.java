package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 * Entity class Pet
 * @author aksbhand2
 *
 */
@Entity
@Table(name = "PET")
public class Pet {
	@Id
	@SequenceGenerator(sequenceName = "pet_sequence", initialValue = 200, name = "pet_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pet_id")
	@Column(name = "PID")
	private int petId;

	@Column(name = "PETNAME")
	private String name;

	@Column(name = "PETTYPE")
	private String petType;

	@Column(name = "OID")
	private int ownerId;

	/**
	 * No argument constructor
	 */
	public Pet() {

	}

	/**
	 * Argument contructor
	 * @param name
	 * @param petType
	 * @param ownerId
	 */
	public Pet(String name, String petType, int ownerId) {

		this.name = name;
		this.petType = petType;
		this.ownerId = ownerId;
	}


	/**
	 * To get petid
	 * @return petId
	 */
	public int getPetId() {
		return petId;
	}

	/**
	 * To set petId
	 * @param petId
	 */
	public void setPetId(int petId) {
		this.petId = petId;
	}

	/**
	 * To get pet name
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * To set name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * To get pet type
	 * @return petType
	 */
	public String getPetType() {
		return petType;
	}

	/**
	 * To set pet type
	 * @param petType
	 */
	public void setPetType(String petType) {
		this.petType = petType;
	}

	/**
	 * To get ownerId
	 * @return ownerId
	 */
	public int getOwnerId() {
		return ownerId;
	}

	/**
	 * To set OwnerId
	 * @param ownerId
	 */
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

}
