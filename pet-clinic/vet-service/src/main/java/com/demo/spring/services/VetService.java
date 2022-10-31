package com.demo.spring.services;

import java.util.List;

import com.demo.spring.entity.Vet;
import com.demo.spring.util.Message;

public interface VetService {
	
	public List<Vet> listAll();
	public Message addVet(Vet vet);
	public Message removeVet(int id);
	public List<Vet> vetsForSpl(String splName);
	public Message updateVet(int id,String phNumber,Vet vet);
	public Vet findVetById(int id);
}
