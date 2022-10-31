package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SPECIALITY")
public class Speciality {
	@Id
	@SequenceGenerator(sequenceName = "spl_sequence",initialValue = 10, name = "spl_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "spl_id")
	@Column(name = "SPLID")
	private int splId;
	
	public void setSplId(int splId) {
		this.splId = splId;
	}

	@Column(name = "SPLNAME")
	private String splName;
	
	public Speciality() {
		
	}

	public Speciality(String splName) {
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
