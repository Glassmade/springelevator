package com.fdmgroup.springelevator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.fdmgroup.springelevator.model.Floor;
import com.fdmgroup.springelevator.model.User;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserManagementTesting {

	/*
	 * 
	 * 		As a User I want to have a random origin and request for floors
	 * in order to have them in a queued to use the next available elevator.
	 * 
	 * 
	 */

	UserManagement randomizer;
	User testUser1, testUser2, testUser3;
	Floor inputOrigin;
	Floor inputDestination;

	@BeforeEach
	public void init() {
		randomizer = new UserManagement();
	}

	@Test
	public void testIfUserControllerSuccessfullyInstantiatedAUser() {
		testUser1 = randomizer.createUser();
		assertNotNull(testUser1);
	}

	@Test
	public void testIfUserControllerSuccessullyInstatiatesUserWithDesinationFloor() {
		inputOrigin = new Floor(5);
		inputDestination = new Floor(0);
		testUser1 = randomizer.createUserWithRequest(inputOrigin, inputDestination);
		assertNotNull(testUser1);
		assertEquals(inputOrigin.getFloorIdentifier(), testUser1.getOrigin().getFloorIdentifier());
		assertEquals(inputDestination.getFloorIdentifier(), testUser1.getDestination().getFloorIdentifier());
	}

	@Test
	public void testIfUserControllerSuccessfullyInstatiatesUserWithRandomDestinationAndOrigin() {
		// Treating the floors below as min and max.
		inputOrigin = new Floor(0);
		inputDestination = new Floor(5);
		randomizer = new UserManagement(inputDestination);
		testUser1 = randomizer.createUserWithRandomRequests();
		System.err.print("Randomize origin destination floor\n" + testUser1.getOrigin().getFloorIdentifier()
				+ "<origin - destination>" + testUser1.getDestination().getFloorIdentifier());
	}

	@Test
	public void testIfUserControllerSuccessfullyInstatiatesUserWithGivenRandomRangeA_DestinationAndOrigin() {
		// Treating the floors below as min and max.
		inputOrigin = new Floor(0);
		inputDestination = new Floor(5);
		randomizer = new UserManagement(inputDestination);
		testUser1 = randomizer.createUserWithRandomRequests();
		assertTrue(testUser1.getOrigin().getFloorIdentifier() >= inputOrigin.getFloorIdentifier());
		assertTrue(testUser1.getOrigin().getFloorIdentifier() <= inputDestination.getFloorIdentifier());
		assertTrue(testUser1.getDestination().getFloorIdentifier() >= inputOrigin.getFloorIdentifier());
		assertTrue(testUser1.getDestination().getFloorIdentifier() <= inputDestination.getFloorIdentifier());
	}

	@Test
	public void testIfUserControllerSuccessfullyQueuesRandomGeneratedRequests() {
		// Treating the floors below as min and max.
		inputOrigin = new Floor(0);
		inputDestination = new Floor(5);
		randomizer = new UserManagement(inputDestination);
		testUser1 = randomizer.createUserWithRandomRequests();
		testUser2 = randomizer.createUserWithRandomRequests();
		testUser3 = randomizer.createUserWithRandomRequests();
		randomizer.addUserToTheQueue(testUser1);
		randomizer.addUserToTheQueue(testUser2);
		randomizer.addUserToTheQueue(testUser3);
		assertEquals(3, randomizer.getQueuedUserForTheElevator().size());
		assertEquals(testUser1, randomizer.getQueuedUserForTheElevator().peek());	
	}
	
	@Test
	public void testIfUserControllerSuccessfullyQueuesRandomGeneratedRequestsAndKeepsFIFOmannerism() {
		// Treating the floors below as min and max.
		inputOrigin = new Floor(0);
		inputDestination = new Floor(5);
		randomizer = new UserManagement(inputDestination);
		testUser1 = randomizer.createUserWithRandomRequests();
		testUser2 = randomizer.createUserWithRandomRequests();
		testUser3 = randomizer.createUserWithRandomRequests();
		randomizer.addUserToTheQueue(testUser1);
		randomizer.addUserToTheQueue(testUser2);
		randomizer.addUserToTheQueue(testUser3);
		assertEquals(testUser1, randomizer.getQueuedUserForTheElevator().poll());
		assertEquals(testUser2, randomizer.getQueuedUserForTheElevator().poll());
		assertEquals(testUser3, randomizer.getQueuedUserForTheElevator().poll());
		assertTrue(randomizer.getQueuedUserForTheElevator().isEmpty());
	}

	@Test
	void testIfIcanSetAQueueForTheElevatorAndAMaxDestinationAfterIinitializeAnEmptyUserManagement() {
		randomizer = new UserManagement();
		randomizer.setForTheElevator(new LinkedList<User>());
		randomizer.setMaxDestination(10);
		assertTrue(randomizer.getQueuedUserForTheElevator().isEmpty());
		assertEquals(10,randomizer.getMaxDestination());
	}
	
	
}
