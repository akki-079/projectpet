package com.demo.spring.service;

import java.util.List;

import com.demo.spring.entity.Visit;
import com.demo.spring.util.Message;

public interface VisitService {
	public List<Visit> listAllVisits();
	public Message recordVisit(Visit visit);
	public List<Visit> listVisitById(int id);
	public List<Visit> listVisitByOId(int id);
	public List<Visit> listVisitByPIds(String  ids);
}
