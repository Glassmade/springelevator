package com.fdmgroup.springelevator.model;

import com.fdmgroup.springelevator.model.enums.GOING;

public class User {

	private Floor origin;
	private Floor destination;
	private GOING direction;

	public User() {
	}

	public User(Floor inputedOrigin, Floor inputedDestination) {
		
		this.origin = inputedOrigin;
		this.destination = inputedDestination;
		setDirectionDynamically();
	}

	private void setDirectionDynamically() {
		if(origin.compareTo(destination)==-1)
			setDirection(GOING.UPWARDS);
		else if(origin.compareTo(destination)==1)
			setDirection(GOING.DOWNWARDS);
		else
			setDirection(GOING.NOWHERE);
	}

	public Floor getOrigin() {
		return origin;
	}

	public void setOrigin(Floor origin) {
		this.origin = origin;
	}

	public Floor getDestination() {
		return destination;
	}

	public void setDestination(Floor destination) {
		this.destination = destination;
	}

	public GOING getDirection() {
		return direction;
	}

	public void setDirection(GOING direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		setDirectionDynamically();
		return String.format(super.toString()+" going %s", direction.toString());
	}
	
	

}
