package com.fdmgroup.springelevator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.fdmgroup.springelevator.model.enums.GOING;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserTesting {

	/* User Story for User Testing
	 * As a system administrator I want to simulate user request in a form of a User
	 * so I can automate the procedure of calling elevators.
	 */

	User testUser;
	int origin, destination, expectedInt;

	@Test
	void testIfIcanInstantiateAuser() {
		testUser = new User();
		assertNotNull(testUser);
	}

	@Test
	void testIfIcanInstatiateAUserWithFloorAsAnOriginandAFloorAsADestination() {
		origin = 0;
		destination = 5;
		testUser = new User(new Floor(origin), new Floor(destination));
		assertEquals(origin, testUser.getOrigin().getFloorIdentifier());
		assertEquals(destination, testUser.getDestination().getFloorIdentifier());
	}
	
	@Test
	void testIfIcanInstatiateAUserWithDirectionProvidedIgiveOriginAndDestination(){
		origin = 0;
		destination = 5;
		testUser = new User(new Floor(origin), new Floor(destination));
		assertEquals(GOING.UPWARDS, testUser.getDirection());
	}
	
	@Test
	void testIfIcanInstatiateAUserWithDirectionProvidedIgiveOriginAndDestinationReversedWeGetTheOpositeDirection(){
		origin = 5;
		destination = 0;
		testUser = new User(new Floor(origin), new Floor(destination));
		assertEquals(GOING.DOWNWARDS, testUser.getDirection());
	}
	
	@Test
	void testIfIcanInstatiateAUserWithDirectionProvidedIgiveOriginAndDestinationAreSameWeGoNowhere(){
		origin = 0;
		destination = 0;
		testUser = new User(new Floor(origin), new Floor(destination));
		assertEquals(GOING.NOWHERE, testUser.getDirection());
	}
	
	@Test
	void testIfIcanHaveADynamicToStringMethodInUserThatWillUpdateWhereIsHeGoingWhenICallIt() {
		origin = 0;
		destination = 5;
		testUser = new User(new Floor(origin), new Floor(destination));
		String beforeReachingDestination = testUser.toString();
		testUser.setOrigin(new Floor(destination));
		String afterReachingDestination = testUser.toString();
		assertNotEquals(beforeReachingDestination, afterReachingDestination);
	}
	
	@Test
	void testIfIcanInstatiateAUserWithAnySpecificationsAndAddAllSpecificationsOnAnSetMethodsWillReturnTheResultsExpected() {
		testUser = new User();
		assertNotNull(testUser);
		assertNull(testUser.getDestination());
		assertNull(testUser.getOrigin());
		assertNull(testUser.getDirection());
		expectedInt = 5;
		testUser.setDestination(new Floor(expectedInt));
		assertEquals(expectedInt, testUser.getDestination().getFloorIdentifier());
		testUser.setOrigin(new Floor(expectedInt));
		assertEquals(expectedInt, testUser.getOrigin().getFloorIdentifier());
		testUser.toString(); // Calling this to run a private method within
		assertEquals(GOING.NOWHERE,testUser.getDirection());
		
	}
	
	@Test
	void testIfIcanGetTheSpecificStringFromTheDirectionOfAUser() {
		testUser = new User();
		testUser.setDirection(GOING.NOWHERE);
		assertEquals("Nowhere",testUser.getDirection().getDirection());
	}


}
