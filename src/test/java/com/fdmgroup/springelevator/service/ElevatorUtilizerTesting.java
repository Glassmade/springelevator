package com.fdmgroup.springelevator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.fdmgroup.springelevator.model.Elevator;
import com.fdmgroup.springelevator.model.Floor;
import com.fdmgroup.springelevator.model.User;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ElevatorUtilizerTesting {

	/*
	 * User Scenario As a user I would like use a system that utilizes many
	 * elevators so I can have the most efficient elevator to transport me.
	 */

	ElevatorUtilizer testElevatorUtilizer;
	int testMaxFloors, testMaxElevators, testMaxCapacity;
	User testUser;
	Elevator testElevator, testElevator2, testElevator3, testElevator4;
	UserManagement testUserManagement;

	@Test
	void testIfIcanCreateAnElevatorUtilizer() {
		testElevatorUtilizer = new ElevatorUtilizer();
		assertNotNull(testElevatorUtilizer);
	}

	@Test
	void testIfIcanCreateAnElevatorUtilizerWithFourElevators() {
		testElevatorUtilizer = new ElevatorUtilizer(4);
		assertEquals(4, testElevatorUtilizer.getAvailableElevators().size());
	}

	@Test
	void testIfIcanCreateAnElevatorUtilizerWithElevatorsAndTopFloor() {
		testMaxFloors = 10;
		testMaxElevators = 4;
		testElevatorUtilizer = new ElevatorUtilizer(testMaxElevators, testMaxFloors);
		assertEquals(10, testElevatorUtilizer.getMaxFloors());
	}

	@Test
	void testIfIcanCreateAnElevatorUtilizerAndGiveElevatorsMaximumCapacity() {
		testMaxFloors = 10;
		testMaxElevators = 1;
		testMaxCapacity = 5;
		testElevatorUtilizer = new ElevatorUtilizer(testMaxElevators, testMaxFloors, testMaxCapacity);
		assertEquals(testMaxCapacity, testElevatorUtilizer.getMaxCapacity());

	}

	@Test
	void testIfIcanCreateAnExecutorServiceWhenElevatorUtilzerIsInstatiated() {
		testMaxFloors = 10;
		testMaxElevators = 4;
		testElevatorUtilizer = new ElevatorUtilizer(testMaxElevators, testMaxFloors);
		assertNotNull(testElevatorUtilizer.getExecutor());
	}

	@Test
	void testIfIcanTransportAUserUsingTheExecutor() throws InterruptedException {
		testMaxFloors = 10;
		testMaxElevators = 4;
		testElevatorUtilizer = new ElevatorUtilizer(testMaxElevators, testMaxFloors);
		testUser = new User(new Floor(0), new Floor(5));
		testElevator = (Elevator) (testElevatorUtilizer.getAvailableElevators().get(0));
		testElevator.setUsers(new CopyOnWriteArrayList<User>());
		testElevator.setRestingFloor(testUser.getOrigin());
		testElevator.boardTheElevator(testUser);
		testElevatorUtilizer.getExecutor().execute(testElevator);
		testElevatorUtilizer.getExecutor().awaitTermination(12, TimeUnit.SECONDS);
		assertTrue(testElevator.usersInElevator().isEmpty());
	}

	@Test
	void testIfIcanCreateRandomUserRequestAndInjectItToTheExecutor() throws InterruptedException {
		testMaxFloors = 10;
		testMaxElevators = 4;
		testElevatorUtilizer = new ElevatorUtilizer(testMaxFloors, testMaxElevators);

		testUserManagement = new UserManagement(new Floor(testMaxFloors));
		testUser = testUserManagement.createUserWithRandomRequests();
		testElevator = (Elevator) (testElevatorUtilizer.getAvailableElevators().get(0));
		testElevator.setUsers(new CopyOnWriteArrayList<User>());
		testElevator.setRestingFloor(testUser.getOrigin());
		testElevator.boardTheElevator(testUser);
		testElevatorUtilizer.getExecutor().execute(testElevator);
		testElevatorUtilizer.getExecutor().awaitTermination(12, TimeUnit.SECONDS);
		assertTrue(testElevator.usersInElevator().isEmpty());
	}

	@Test
	void testIfIcanCreateThreeOfRandomUserRequestQueueThemAndHaveThemTransported() throws InterruptedException {
		testMaxFloors = 10;
		testMaxElevators = 4;
		testElevatorUtilizer = new ElevatorUtilizer(testMaxElevators, testMaxFloors);
		testUserManagement = new UserManagement(new Floor(testMaxFloors));

		testUserManagement.getQueuedUserForTheElevator().add(testUserManagement.createUserWithRandomRequests());
		testUserManagement.getQueuedUserForTheElevator().add(testUserManagement.createUserWithRandomRequests());
		testUserManagement.getQueuedUserForTheElevator().add(testUserManagement.createUserWithRandomRequests());

		testElevator = (Elevator) (testElevatorUtilizer.getAvailableElevators().get(0));
		testElevator.setUsers(new CopyOnWriteArrayList<User>());

		testElevator.setRestingFloor(testUserManagement.getQueuedUserForTheElevator().peek().getOrigin());
		testElevator.boardTheElevator(testUserManagement.getQueuedUserForTheElevator().poll());
		testElevatorUtilizer.getExecutor().execute(testElevator);
		testElevatorUtilizer.getExecutor().awaitTermination(10, TimeUnit.SECONDS);

		testElevator.setRestingFloor(testUserManagement.getQueuedUserForTheElevator().peek().getOrigin());
		testElevator.boardTheElevator(testUserManagement.getQueuedUserForTheElevator().poll());
		testElevatorUtilizer.getExecutor().execute(testElevator);
		testElevatorUtilizer.getExecutor().awaitTermination(10, TimeUnit.SECONDS);

		testElevator.setRestingFloor(testUserManagement.getQueuedUserForTheElevator().peek().getOrigin());
		testElevator.boardTheElevator(testUserManagement.getQueuedUserForTheElevator().poll());
		testElevatorUtilizer.getExecutor().execute(testElevator);
		testElevatorUtilizer.getExecutor().awaitTermination(10, TimeUnit.SECONDS);

		assertTrue(testElevator.usersInElevator().isEmpty());
		assertTrue(testUserManagement.getQueuedUserForTheElevator().isEmpty());

	}

	@Test
	void testIfIcanServeTheUserWithTheClosestElevator() throws InterruptedException {
		
		testMaxFloors = 10;
		testMaxElevators = 4;
		testElevatorUtilizer = new ElevatorUtilizer(testMaxElevators, testMaxFloors);
		testUser = new User(new Floor(0), new Floor(5));
		
		testElevatorUtilizer.getUserSimulator().addUserToTheQueue(testUser);
		
		testElevator = (Elevator) (testElevatorUtilizer.getAvailableElevators().get(0));
		testElevator2 = (Elevator) (testElevatorUtilizer.getAvailableElevators().get(1));
		testElevator3 = (Elevator) (testElevatorUtilizer.getAvailableElevators().get(2));
		testElevator4 = (Elevator) (testElevatorUtilizer.getAvailableElevators().get(3));
		
		testElevator.setUsers(new CopyOnWriteArrayList<User>());
		testElevator2.setUsers(new CopyOnWriteArrayList<User>());
		testElevator3.setUsers(new CopyOnWriteArrayList<User>());
		testElevator4.setUsers(new CopyOnWriteArrayList<User>());
		
        testElevator.setRestingFloor(new Floor(1));
        testElevator2.setRestingFloor(new Floor(10));
        testElevator3.setRestingFloor(new Floor(0));
        testElevator4.setRestingFloor(new Floor(5));
        
        testElevatorUtilizer.transportService();
		testElevatorUtilizer.getExecutor().awaitTermination(10, TimeUnit.SECONDS);
        assertTrue(testElevator3.usersInElevator().isEmpty());

	}
	
	
	@Test
	void testIfIcanServeMultipleUsersWithTheClosestElevator() throws InterruptedException {
		
		testMaxFloors = 10;
		testMaxElevators = 4;
		testElevatorUtilizer = new ElevatorUtilizer(testMaxElevators, testMaxFloors);
		
		testElevatorUtilizer.getUserSimulator().addUserToTheQueue(testElevatorUtilizer.getUserSimulator().createUserWithRandomRequests());
		testElevatorUtilizer.getUserSimulator().addUserToTheQueue(testElevatorUtilizer.getUserSimulator().createUserWithRandomRequests());
		testElevatorUtilizer.getUserSimulator().addUserToTheQueue(testElevatorUtilizer.getUserSimulator().createUserWithRandomRequests());
		testElevatorUtilizer.getUserSimulator().addUserToTheQueue(testElevatorUtilizer.getUserSimulator().createUserWithRandomRequests());
		
		testElevator = (Elevator) (testElevatorUtilizer.getAvailableElevators().get(0));
		testElevator2 = (Elevator) (testElevatorUtilizer.getAvailableElevators().get(1));
		testElevator3 = (Elevator) (testElevatorUtilizer.getAvailableElevators().get(2));
		testElevator4 = (Elevator) (testElevatorUtilizer.getAvailableElevators().get(3));
		
		testElevator.setUsers(new CopyOnWriteArrayList<User>());
		testElevator2.setUsers(new CopyOnWriteArrayList<User>());
		testElevator3.setUsers(new CopyOnWriteArrayList<User>());
		testElevator4.setUsers(new CopyOnWriteArrayList<User>());
		
        testElevator.setRestingFloor(new Floor(1));
        testElevator2.setRestingFloor(new Floor(10));
        testElevator3.setRestingFloor(new Floor(0));
        testElevator4.setRestingFloor(new Floor(5));
        
        testElevatorUtilizer.transportService();
		testElevatorUtilizer.getExecutor().awaitTermination(60, TimeUnit.SECONDS);
        
		boolean isItTrue = false;
		for (Runnable elevator : testElevatorUtilizer.getAvailableElevators()) {
			Elevator tempHolder = null;
			tempHolder = (Elevator)elevator;
			if (tempHolder.usersInElevator().isEmpty())
				isItTrue=true;
			else
				isItTrue=false;
		}
			
		assertTrue(isItTrue);
	}
	

}
