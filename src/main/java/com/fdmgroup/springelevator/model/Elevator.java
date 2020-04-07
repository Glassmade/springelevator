package com.fdmgroup.springelevator.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.util.StopWatch;

import com.fdmgroup.springelevator.model.enums.GOING;
import com.fdmgroup.springelevator.service.JourneyService;

public class Elevator implements Runnable {

	JourneyService journeyService;

	private int capacity;
	private Floor restingFloor;
	private List<User> users;
	private GOING elevatorDirection;
	private Journey elevatorJourney;
	private StopWatch stopwatch;

	public Elevator() {
	}

	public Elevator(int inputedCapacity) {
		setCapacity(inputedCapacity);
		setStopwatch(new StopWatch());
		journeyService = new JourneyService();
	}

	public Elevator(int inputedCapacity, Floor inputedRestingFloor) {
		this(inputedCapacity);
		setRestingFloor(inputedRestingFloor);
	}

	public Elevator(int inputedCapacity, Floor inputedRestingFloor, User inputedUser) {
		this(inputedCapacity, inputedRestingFloor);
		users = new CopyOnWriteArrayList<User>();
	}

	@Override
	public void run() {

		/*
		 * elevatorJourney = new Journey(); journeyService = new JourneyService();
		 * getStopwatch().start();
		 * 
		 * elevatorJourney.setOriginFloor(getRestingFloor().getFloorIdentifier());
		 * elevatorJourney.setMaxUsers(usersInElevator().size());
		 */

		while (!usersInElevator().isEmpty()) {

			cleanDelaySimulate();

			if (elevatorGoingUpwards()) {

				incrementTheFloor();
				checkAllUsersDestination();

			} else {

				decrementTheFloor();
				checkAllUsersDestination();
			}
			outputElevatorMessage();
		}
		/*
		 * elevatorJourney.setDestinationFloor(getRestingFloor().getFloorIdentifier());
		 * getStopwatch().stop();
		 * elevatorJourney.setTimeElapsed(getStopwatch().getTotalTimeMillis());
		 * journeyService.createJourney(elevatorJourney);
		 */

	}

	public void boardTheElevator(User inputedUser) {

		if (usersInElevator().isEmpty()) {
			usersInElevator().add(inputedUser);
			setElevatorDirection(inputedUser.getDirection());
		} else if (getRestingFloor().equals(inputedUser.getOrigin()))
			usersInElevator().add(inputedUser);

	}

	private boolean elevatorGoingUpwards() {
		return getElevatorDirection().equals(GOING.UPWARDS);
	}

	private void decrementTheFloor() {
		setRestingFloor(new Floor((getRestingFloor().getFloorIdentifier() - 1)));
	}

	private void incrementTheFloor() {
		setRestingFloor(new Floor(getRestingFloor().getFloorIdentifier() + 1));
	}

	private void checkAllUsersDestination() {

		for (User userInElevator : usersInElevator()) {

			if (userInElevator.getDestination().equals(getRestingFloor())) {
				usersInElevator().remove(userInElevator);
				userInElevator.setOrigin(getRestingFloor());

			} else {
				userInElevator.setOrigin(getRestingFloor());
			}

			outputUserMessage(userInElevator);
		}

		flagElevatorStopped();
	}

	private void outputUserMessage(User userInElevator) {
		System.out.println(userInElevator.toString());
	}

	private void outputElevatorMessage() {
		// Future log
		System.out.println(toString());
	}

	private void cleanDelaySimulate() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public Floor getRestingFloor() {
		return restingFloor;
	}

	public void setRestingFloor(Floor restingFloor) {
		this.restingFloor = restingFloor;
	}

	public List<User> usersInElevator() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public GOING getElevatorDirection() {
		return elevatorDirection;
	}

	public void setElevatorDirection(GOING elevatorDirection) {
		this.elevatorDirection = elevatorDirection;
	}

	public void flagElevatorStopped() {
		if (usersInElevator().isEmpty())
			setElevatorDirection(GOING.NOWHERE);
	}

	public Journey getElevatorJourney() {
		return elevatorJourney;
	}

	public void setElevatorJourney(Journey elevatorJourney) {
		this.elevatorJourney = elevatorJourney;
	}

	public StopWatch getStopwatch() {
		return stopwatch;
	}

	public void setStopwatch(StopWatch stopwatch) {
		this.stopwatch = stopwatch;
	}

	@Override
	public String toString() {
		return String.format("Elevator is at %s, Passengers:%s", getRestingFloor(), usersInElevator().size());
	}

}
