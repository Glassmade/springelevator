package com.fdmgroup.springelevator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.springelevator.model.Journey;
import com.fdmgroup.springelevator.repository.JourneyDao;
import com.fdmgroup.springelevator.repository.JourneyServiceRepository;

@Service
public class JourneyService implements JourneyServiceRepository<Journey> {

	@Autowired
	JourneyDao journeyDao;
	
	@Override
	public List<Journey> findAllJourneys() {
		return journeyDao.findAll();	
	}

	@Override
	public void createJourney(Journey inputedJourney) {
		journeyDao.save(inputedJourney);
	}
	
	

}
