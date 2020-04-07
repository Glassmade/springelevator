package com.fdmgroup.springelevator.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import com.fdmgroup.springelevator.model.Elevator;
import com.fdmgroup.springelevator.model.Floor;
import com.fdmgroup.springelevator.model.User;

@Service
public class ElevatorUtilizer {

	private List<Runnable> availableElevators;
	private ExecutorService executor;
	private int maxFloors;
	private int maxCapacity;
	private UserManagement userRequestSimulator;
	
	public ElevatorUtilizer() {
	}
	
	public ElevatorUtilizer(int inputedMaxElevators) {
		setAvailableElevators(new ArrayList<Runnable>());
		populateElevatorList(inputedMaxElevators);
	}

	public ElevatorUtilizer(int inputedMaxElevators, int inputedMaxFloors) {
		this(inputedMaxElevators);
		setMaxFloors(inputedMaxFloors);
		setExecutor(Executors.newFixedThreadPool(inputedMaxElevators));
		setUserSimulator(new UserManagement(new Floor(inputedMaxFloors)));
	}

	public ElevatorUtilizer(int inpuptedMaxElevators, int inputedMaxFloors, int inputedMaxCapacity) {
		this(inpuptedMaxElevators, inputedMaxFloors);
		setMaxCapacity(inputedMaxCapacity);
		
	}

	private void populateElevatorList(int inputedMaxElevators) {
		for (int i =0; i<inputedMaxElevators; i++) {
			Elevator elevator = new Elevator();
			elevator.setCapacity(getMaxCapacity());
			this.availableElevators.add(elevator);
		}
		
	}
	
public void transportService() {
		
		HashMap<Integer, Integer> elevatorDistanceFromRequest = new HashMap<Integer, Integer>();
		User toBeServed;
		
		populateHashMapWithDistancesFromUserRequest(elevatorDistanceFromRequest);
		Entry<Integer, Integer> minPointer = null;
		
		minPointer = getMinimumDistanceForRequest(elevatorDistanceFromRequest, minPointer);
		Elevator serviceElevator = grabElevatorWithShortestDistance(minPointer);
		
		toBeServed = userRequestSimulator.getQueuedUserForTheElevator().poll();
		serviceElevator.boardTheElevator(toBeServed);
		
		serviceElevator.setRestingFloor(toBeServed.getOrigin());
		
		for (User nextOnTheQueue : userRequestSimulator.getQueuedUserForTheElevator()) {
			if (nextOnTheQueue.getOrigin().getFloorIdentifier()==serviceElevator.getRestingFloor().getFloorIdentifier() && nextOnTheQueue.equals(userRequestSimulator.getQueuedUserForTheElevator().peek())) {
				
				serviceElevator.boardTheElevator(userRequestSimulator.getQueuedUserForTheElevator().poll());
				
			}
			else {
				transportService();
			}
		}
		
		serviceElevatorRespondToRequestMessage(serviceElevator);
	
		this.executor.execute(serviceElevator);

		
		
			
	}

	private void serviceElevatorRespondToRequestMessage(Elevator serviceElevator) {
		System.out.println("Using the elevator from floor "+serviceElevator.getRestingFloor().getFloorIdentifier());
	}

	private Elevator grabElevatorWithShortestDistance(Entry<Integer, Integer> minPointer) {
		return (Elevator)getAvailableElevators().get(minPointer.getKey());
	}

	private Entry<Integer, Integer> getMinimumDistanceForRequest(HashMap<Integer, Integer> elevatorDistanceFromRequest,
			Entry<Integer, Integer> minPointer) {
		for (Entry<Integer, Integer> possibleServer : elevatorDistanceFromRequest.entrySet()) {
			if (minPointer == null || minPointer.getValue() > possibleServer.getValue() )
				minPointer = possibleServer;
		}
		return minPointer;
	}

	private void populateHashMapWithDistancesFromUserRequest(HashMap<Integer, Integer> elevatorDistanceFromRequest) {
	
		for (int i=0; i<getAvailableElevators().size(); i++) {
			
			int actualDifference = 0;
			Elevator  tempHolderForElevator = (Elevator)getAvailableElevators().get(i);
			
			if (tempHolderForElevator.getRestingFloor().getFloorIdentifier()>userRequestSimulator.getQueuedUserForTheElevator().peek().getOrigin().getFloorIdentifier())
				actualDifference = tempHolderForElevator.getRestingFloor().getFloorIdentifier()-userRequestSimulator.getQueuedUserForTheElevator().peek().getOrigin().getFloorIdentifier();
			else
				actualDifference = userRequestSimulator.getQueuedUserForTheElevator().peek().getOrigin().getFloorIdentifier()-tempHolderForElevator.getRestingFloor().getFloorIdentifier();
				
			elevatorDistanceFromRequest.put(i, actualDifference);
		
			
		}
	}

	
	

	public List<Runnable> getAvailableElevators() {
		return availableElevators;
	}

	public void setAvailableElevators(ArrayList<Runnable> inputedElevatorList) {
		this.availableElevators = inputedElevatorList;
	}

	public int getMaxFloors() {
		return maxFloors;
	}

	public void setMaxFloors(int maxFloors) {
		this.maxFloors = maxFloors;
	}

	public ExecutorService getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public UserManagement getUserSimulator() {
		return userRequestSimulator;
	}

	public void setUserSimulator(UserManagement userSimulator) {
		this.userRequestSimulator = userSimulator;
	}

}
