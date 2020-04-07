
package com.fdmgroup.springelevator.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "JOURNEYS")
public class Journey {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "journey_gen")
	@SequenceGenerator(name = "journey_gen", sequenceName = "JOURNEY_SEQ", allocationSize = 1)
	private long journeyId;
	@Column(nullable=false, length=80)
	private long timeElapsed;
	@Column(nullable=false, length=6)
	private int maxUsers;
	@Column(nullable=false, length=6)
	private int originFloor;
	@Column(nullable=false, length=6)
	private int destinationFloor;


	public Journey() {

	}

	public long getJourneyId() {
		return journeyId;
	}


	public void setJourneyId(long journeyId) {
		this.journeyId = journeyId;
	}


	public long getTimeElapsed() {
		return timeElapsed;
	}


	public void setTimeElapsed(long timeElapsed) {
		this.timeElapsed = timeElapsed;
	}


	public int getMaxUsers() {
		return maxUsers;
	}


	public void setMaxUsers(int maxUsers) {
		this.maxUsers = maxUsers;
	}


	public int getOriginFloor() {
		return originFloor;
	}


	public void setOriginFloor(int originFloor) {
		this.originFloor = originFloor;
	}


	public int getDestinationFloor() {
		return destinationFloor;
	}


	public void setDestinationFloor(int destinationFloor) {
		this.destinationFloor = destinationFloor;
	}

	

}
