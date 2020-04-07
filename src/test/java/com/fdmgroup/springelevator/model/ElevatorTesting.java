package com.fdmgroup.springelevator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ElevatorTesting {

	/* User story for Elevator testing
	 *  As a user I would like to use an elevator so
	 * I can go to a specified floor.
	 */

	Elevator testElevator;
	int testCapacity;
	short testFloorId, testAnotherFloorId;
	List<User> testUsers;
	Floor testFloor;
	User testUser, testUser2, testUser3, testUser4;

	@Test
	void testIfIcanCreateAnElevator() {
		testElevator = new Elevator();
		assertNotNull(testElevator);
	}

	@Test
	void testIfIcanCreateAnElevatorWithMaximumCapacity() {
		int expectedCapacity = 5;
		testElevator = new Elevator(expectedCapacity);
		assertEquals(expectedCapacity, testElevator.getCapacity());
	}

	@Test
	void testIfIcanCreateAnElevatorWithMaximumCapacityAndRestingFloor() {
		testCapacity = 5;
		testFloorId = 5;
		testFloor = new Floor(testFloorId);
		testElevator = new Elevator(testCapacity, testFloor);
		assertEquals(testCapacity, testElevator.getCapacity());
		assertEquals(testFloorId, testElevator.getRestingFloor().getFloorIdentifier());
	}

	@Test
	void testIfcanCreateAnElevatorWithMaximumCapacityRestingFloorAndAUserAsRequest() throws InterruptedException {
		testCapacity = 5;
		testFloorId = 5;
		testAnotherFloorId = 0;
		testFloor = new Floor(testFloorId);
		testUser = new User(new Floor(testFloorId), new Floor(testAnotherFloorId));
		testElevator = new Elevator(testCapacity, testFloor, testUser);
		testElevator.setRestingFloor(testUser.getOrigin());
		testElevator.boardTheElevator(testUser);
		assertEquals(testElevator.getRestingFloor(), testUser.getOrigin());
		assertEquals(testUser, testElevator.usersInElevator().get(0));

	}

	@Test
	void testIfIcanCreateAnElevatorAndPassAListOfUsersAfterwards() {
		testCapacity = 5;
		testFloorId = 5;
		testFloor = new Floor(testFloorId);
		testElevator = new Elevator(testCapacity, testFloor);
		assertNull(testElevator.usersInElevator());
		testUsers = new ArrayList<>();
		testElevator.setUsers(testUsers);
		assertNotNull(testElevator.usersInElevator());
	}

	@Test
	void testIfIcanTransportAUserToTheDestinationFloor() throws InterruptedException {
		testCapacity = 5;
		testFloorId = 0;
		testAnotherFloorId = 5;
		testFloor = new Floor(testFloorId);
		testUser = new User(new Floor(testFloorId), new Floor(testAnotherFloorId));
		testElevator = new Elevator(testCapacity, testFloor, testUser);
		testElevator.setRestingFloor(testUser.getOrigin());
		testElevator.boardTheElevator(testUser);
		testElevator.run();
		assertTrue(testElevator.usersInElevator().isEmpty());
		assertEquals(testUser.getOrigin(), testUser.getDestination());
	}
	
	@Test
	void testIfIcanTransportAUserToTheDestinationFloorInADescendingFashion() throws InterruptedException {
		testCapacity = 5;
		testFloorId = 5;
		testAnotherFloorId = 0;
		testFloor = new Floor(testFloorId);
		testUser = new User(new Floor(testFloorId), new Floor(testAnotherFloorId));
		testElevator = new Elevator(testCapacity, testFloor, testUser);
		testElevator.setRestingFloor(testUser.getOrigin());
		testElevator.boardTheElevator(testUser);
		testElevator.run();
		assertTrue(testElevator.usersInElevator().isEmpty());
		assertEquals(testUser.getOrigin(), testUser.getDestination());
	}
	
	@Test
	void testIfIcanTransportMultipleUsersToTheirRespectiveDestinationFloor() throws InterruptedException {
		testCapacity = 5;
		testUser = new User(new Floor(0), new Floor(6));
		testUser2 = new User(new Floor(1), new Floor(3));
		testUser3 = new User(new Floor(0), new Floor(6));
		testUser4 = new User(new Floor(3), new Floor(5));
		testElevator = new Elevator(testCapacity, new Floor(0), testUser);
		testElevator.boardTheElevator(testUser);
		testElevator.boardTheElevator(testUser2);
		testElevator.boardTheElevator(testUser3);
		testElevator.boardTheElevator(testUser4);
		testElevator.run();
		assertEquals(testUser.getOrigin(), testUser.getDestination());
		assertEquals(testUser2.getOrigin(), testUser2.getDestination());
		assertEquals(testUser3.getOrigin(), testUser3.getDestination());
		assertEquals(testUser4.getOrigin(), testUser4.getDestination());
	}

}
