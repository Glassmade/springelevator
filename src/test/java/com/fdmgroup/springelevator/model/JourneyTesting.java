package com.fdmgroup.springelevator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class JourneyTesting {

	/* User Story for Journey
	 * As a system administrator I want to be able to store detailed information about the transportation
	 * of users so I can have them in my database and review on them.
	 */
	Journey testJourney;
	
	@Test
	void testIfIcanInstatiateAJourney() {
		testJourney = new Journey();
		assertNotNull(testJourney);
	}
	
	@Test
	void testIfIcanPopupulateAndAccessTheFieldsOfAnEmptyInstanceOfJourney() {
		testJourney = new Journey();
		long expectedLong = 0;
		long anotherExpectedLong = 218738127;
		assertEquals(0, testJourney.getDestinationFloor());
		assertEquals(0, testJourney.getMaxUsers());
		assertEquals(0, testJourney.getOriginFloor());
		assertEquals(0, testJourney.getDestinationFloor());
		assertEquals(expectedLong, testJourney.getTimeElapsed());
		testJourney.setDestinationFloor(10);
		testJourney.setOriginFloor(5);
		testJourney.setMaxUsers(2);
		testJourney.setTimeElapsed(anotherExpectedLong);
		assertEquals(10, testJourney.getDestinationFloor());
		assertEquals(2, testJourney.getMaxUsers());
		assertEquals(5, testJourney.getOriginFloor());
		assertEquals(anotherExpectedLong, testJourney.getTimeElapsed());
	}
}
