package com.fdmgroup.springelevator.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.fdmgroup.springelevator.model.Elevator;
import com.fdmgroup.springelevator.model.Floor;
import com.fdmgroup.springelevator.model.Journey;
import com.fdmgroup.springelevator.model.User;
import com.fdmgroup.springelevator.service.ElevatorUtilizer;
import com.fdmgroup.springelevator.service.JourneyService;
import com.fdmgroup.springelevator.service.UserManagement;

@Controller
public class ElevatorUtilizerController {

	@Autowired
	JourneyService journeyService;
	
	@GetMapping("")
	public String runSimulation() {
		return "index.jsp";
	}
	
	@GetMapping("AllJourneys")
	public String presentAllTheJourneys(ModelMap outputModel) throws InterruptedException {
		
		aSingleRandomUserUsingTheFirstElevatorInTheList();
		
		List<Journey> allJourneys = new ArrayList<>();
		allJourneys = journeyService.findAllJourneys();
		
		if (allJourneys.isEmpty()) {
			
			outputModel.addAttribute("errorMessage", "No Journeys in the database!");
			return "/WEB-INF/alljourneys.jsp";
		}
		
		outputModel.addAttribute("allJourneys", allJourneys);
		return "/WEB-INF/alljourneys.jsp";
	}

	private void aSingleRandomUserUsingTheFirstElevatorInTheList() throws InterruptedException {
		int testMaxFloors = 10;
		int testMaxElevators = 4;
		ElevatorUtilizer testElevatorUtilizer = new ElevatorUtilizer(testMaxFloors, testMaxElevators);
		
		
		UserManagement testUserManagement = new UserManagement(new Floor(testMaxFloors));
		User testUser = testUserManagement.createUserWithRandomRequests();
		Elevator testElevator = (Elevator)(testElevatorUtilizer.getAvailableElevators().get(0));
		testElevator.setUsers(new CopyOnWriteArrayList<User>());
		testElevator.setRestingFloor(testUser.getOrigin());
		testElevator.boardTheElevator(testUser);
		testElevatorUtilizer.getExecutor().execute(testElevator);
		testElevatorUtilizer.getExecutor().awaitTermination(15, TimeUnit.SECONDS);
	}

	
}
