package com.fdmgroup.springelevator.model.enums;

public enum GOING {

	UPWARDS("Upwards"),
	DOWNWARDS("Downwards"),
	NOWHERE("Nowhere");
	
	private String direction;
	
	private GOING(String inputedDirection) {
		this.direction = inputedDirection;
	}

	public String getDirection() {
		return direction;
	}
}
