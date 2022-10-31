package com.demo.spring.dtos;

public class SpecialityDTO {

	private int splId;

	private String splName;

	public SpecialityDTO() {

	}

	public SpecialityDTO(String splName) {
		this.splName = splName;
	}

	public int getSplId() {
		return splId;
	}

	public String getSplName() {
		return splName;
	}

	public void setSplName(String splName) {
		this.splName = splName;
	}

}
