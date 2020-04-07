package com.fdmgroup.springelevator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.springelevator.model.Journey;

public interface JourneyDao extends JpaRepository<Journey, Long>{

}
