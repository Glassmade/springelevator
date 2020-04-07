package com.fdmgroup.springelevator.repository;

import java.util.List;

public interface JourneyServiceRepository<Journey> {

	List<Journey> findAllJourneys();
	void createJourney(Journey inputedJourney); 
}
