package com.demo.spring.dtos;

public class VetDTO {

	private int vetId;

	private String name;

	private String phNumber;

	private int splId;

	public VetDTO() {

	}

	public VetDTO(String name, String phNumber, int splId) {
		this.name = name;
		this.phNumber = phNumber;
		this.splId = splId;
	}

	public int getVetId() {
		return vetId;
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
