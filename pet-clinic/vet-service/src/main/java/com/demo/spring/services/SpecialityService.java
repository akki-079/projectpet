package com.demo.spring.services;

import java.util.List;

import com.demo.spring.entity.Speciality;
import com.demo.spring.util.Message;

public interface SpecialityService {
	public List<Speciality> listSpecialities();
	public Message addSpl(Speciality spl);
	public Message removeSpl(int id);
	public Speciality getSplByName(String splName);
	
}
