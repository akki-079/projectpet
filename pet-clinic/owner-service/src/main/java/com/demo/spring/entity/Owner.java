package com.demo.spring.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entity class Owner
 * @author aksbhand2
 *
 */
@Entity
@Table(name="OWNER")
public class Owner {
	@Id
	@SequenceGenerator(sequenceName = "owner_sequence",initialValue = 100, name = "owner_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "owner_id")
	@Column(name="OID")
	private int ownerId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="CITY")
	private String city;
	
	
	@Column(name="PHNUMBER")
	private String phNumber;
	
	@Transient
	private List<Pet> petList;
	
	/**
	 * No argument constructor
	 */
	public Owner() {
		
	}
	
	/**
	 * Argument constructor
	 * @param name
	 * @param city
	 * @param phNumber
	 */
	public Owner(String name, String city, String phNumber) {
		
		this.name = name;
		this.city = city;
		this.phNumber = phNumber;
	}
	
	

	/**
	 * To get ownerId
	 * @return ownerId
	 */
	public int getOwnerId() {
		return ownerId;
	}
	
	/**
	 * To set ownerId
	 * @param ownerId
	 */
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * To get name
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
	 * To get city
	 * @return city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * To set city
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * To get Phone Number
	 * @return phNumber
	 */
	public String getPhNumber() {
		return phNumber;
	}
	
	/**
	 * To set Number
	 * @param phNumber
	 */
	public void setPhNumber(String phNumber) {
		this.phNumber = phNumber;
	}

	/**
	 * To get list of pets
	 * @return petList
	 */
	public List<Pet> getPetList() {
		return petList;
	}

	/**
	 * To set list of pets
	 * @param petList
	 */
	public void setPetList(List<Pet> petList) {
		this.petList = petList;
	}

	
	
	
	
	
	
}
