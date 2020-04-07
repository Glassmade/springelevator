package com.fdmgroup.springelevator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FloorTesting {


	/* User Story for the FloorTestings
	 * As a system administrator I want to create a Floor input so my Elevator will
	 * have a resting floor, and a User will have origin and destination;
	 */
	
	Floor testFloor, testAnotherFloor;


	@Test
	void testIfFloorCanBesuccesfullyInstantiated() {
		testFloor = new Floor();
		assertNotNull(testFloor);
	}
	
	@Test
	void testIfFloorCanHaveAnIdentifier() {
		int expected = 5;
		testFloor = new Floor(expected);
		assertEquals(expected, testFloor.getFloorIdentifier());
	}
	
	@Test 
	void testIfIcanSetFloorToHaveAnotherUniqueIdentifier(){
		int firstId = 5;
		testFloor = new Floor(firstId);
		int expected = 10; 
		testFloor.setFloorIdentifier(expected);
		assertEquals(expected,testFloor.getFloorIdentifier());
	}
	
	@Test 
	void testIfIcanOutputTheIdentifierOfTheFloor() {
		int expected = 5;
		testFloor = new Floor(expected);
		assertEquals("at floor: 5", testFloor.toString());
	}
	
	@Test
	void testIfIcanGenerateHashCodeForFloor() {
		int expected = 5;
		testFloor = new Floor(expected);
		int expectedNotNull = testFloor.hashCode();
		assertNotNull(expectedNotNull);
	}
	
	@Test
	void checkIfIgetTrueWhenTwoFloorsAreTheSame() {
		short expected = 5;
		testFloor = new Floor(expected);
		testAnotherFloor = new Floor(expected);
		assertTrue(testFloor.equals(testAnotherFloor));
	}
	
	@Test
	void checkIfIgetFalseWhenTwoFloorsAreNotTheSame() {
		short forTestFloor = 5;
		short forAnotherTestFloor = 6;
		testFloor = new Floor(forTestFloor);
		testAnotherFloor = new Floor(forAnotherTestFloor);
		assertFalse(testFloor.equals(testAnotherFloor));
	}
	
	@Test
	void checkIfWeGetNegativeOneWhenOurFloorHasSmallerIdentifier() {
		short ourFloorId = 3;
		short otherFloorId = 6;
		testFloor = new Floor(ourFloorId);
		testAnotherFloor = new Floor(otherFloorId);
		assertEquals(-1, testFloor.compareTo(testAnotherFloor));
	}
	
	@Test
	void checkIfWeGetPositiveOneWhenOurFloorHasBiggerIdentifier() {
		int ourFloorId = 6;
		int otherFloorId = 3;
		testFloor = new Floor(ourFloorId);
		testAnotherFloor = new Floor(otherFloorId);
		assertEquals(1, testFloor.compareTo(testAnotherFloor));
	}
	
	@Test
	void checkIfWeGetZeroWhenOurFloorsAreTheSame() {
		int ourFloorId = 3;
		int otherFloorId = 3;
		testFloor = new Floor(ourFloorId);
		testAnotherFloor = new Floor(otherFloorId);
		assertEquals(0, testFloor.compareTo(testAnotherFloor));
	}
	
	@Test 
	void checkIfOneOfTheFloorIsNullReturnsFalseOnEqualsMethodCall() {
		int ourFloorId = 3;
		testFloor = new Floor(ourFloorId);
		testAnotherFloor = null;
		assertFalse(testFloor.equals(testAnotherFloor));
	}
	
	@Test
	void checkIfSameFloorInstanceReturnsTrueOnEqualsMethodCall() {
		int ourFloorId = 3;
		testFloor = new Floor(ourFloorId);
		assertTrue(testFloor.equals(testFloor));
	}
	
	@Test
	void checkIfSameParametrizedInstancesOfDifferentObjettsReturnsFalseWhenEqualsMethodIsCalled() {
		testFloor = new Floor();
		Elevator testElevator = new Elevator();
		assertFalse(testFloor.equals(testElevator));
	}
	
} 
