package com.fdmgroup.springelevator.service;

import java.util.LinkedList;
import java.util.Queue;

import org.springframework.stereotype.Service;

import com.fdmgroup.springelevator.model.Floor;
import com.fdmgroup.springelevator.model.User;

@Service
public class UserManagement {

	private int maxDestination;
	private Queue<User> forTheElevator;
	
	public UserManagement() {
		
	} 
	
	public UserManagement(Floor maximumInputedDestination) {
		this.forTheElevator = new LinkedList<User>();
		setMaxDestination(maximumInputedDestination.getFloorIdentifier());
	}
	
	public int getMaxDestination() {
		return maxDestination;
	}

	public void setMaxDestination(int maxDestination) {
		this.maxDestination = maxDestination;
	}

	public Queue<User> getQueuedUserForTheElevator() {
		return forTheElevator;
	}

	public void setForTheElevator(Queue<User> forTheElevator) {
		this.forTheElevator = forTheElevator;
	}

	public User createUser() {
		return new User();
	}
	
	public User createUserWithRequest(Floor inputedOrigin, Floor inputedDestination) {
		return new User(inputedOrigin,inputedDestination);
	}
	
	public User createUserWithRandomRequests() {
		
		Floor actualOrigin = createRandomOriginRequest();
		
		Floor actualDestination = createRandomDesinationRequest();
		
		actualDestination = makeOriginDifferentFromDestination(actualOrigin, actualDestination);
		
		return createUserWithRequest(actualOrigin,actualDestination);
	}

	private Floor makeOriginDifferentFromDestination(Floor actualOrigin, Floor actualDestination) {
		while(actualOrigin.getFloorIdentifier() == actualDestination.getFloorIdentifier()) {
			actualDestination = createRandomDesinationRequest();
		}
		return actualDestination;
	}

	private Floor createRandomDesinationRequest() {
		return new Floor((int)(Math.random()*maxDestination));
	}

	private Floor createRandomOriginRequest() {
		Floor actualOrigin = createRandomDesinationRequest();
		return actualOrigin;
	}

	public void addUserToTheQueue(User inputedUser) {
		forTheElevator.add(inputedUser);
	}

	
}
