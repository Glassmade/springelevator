package com.fdmgroup.springelevator.model;

public class Floor implements Comparable<Floor>{
	
	private int floorIdentifier;
	
	public Floor() {}
	
	public Floor(int inputedFloorIdentifier) {
		setFloorIdentifier(inputedFloorIdentifier);
	}

	public int getFloorIdentifier() {
		return floorIdentifier;
	}

	public void setFloorIdentifier(int floorIdentifier) {
		this.floorIdentifier = floorIdentifier;
	}

	@Override
	public String toString() {
		return String.format("at floor: %s", floorIdentifier);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + floorIdentifier;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Floor other = (Floor) obj;
		if (floorIdentifier != other.floorIdentifier)
			return false;
		return true;
	}

	@Override
	public int compareTo(Floor inputedFloor) {
		
		int thisInstanceId = (int)this.getFloorIdentifier();
		int comparedInstanceId = (int)inputedFloor.getFloorIdentifier();
		int returned = thisInstanceId - comparedInstanceId;
		int actual = 0;
		
		if (returned < 0)
			actual = -1;
		else if (returned > 0)
			actual = 1;
		else
			if (this.equals(inputedFloor))
				actual = 0;
		
		return actual;
	}
	
	
	
	
}
