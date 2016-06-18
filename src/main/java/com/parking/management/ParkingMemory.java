package com.parking.management;

import java.util.HashMap;
import java.util.Map;

public class ParkingMemory {

	private static Map<String, ParkingLocation> parkingMap = new HashMap<>();
	
	private ParkingMemory() {}
	
	public static Map<String, ParkingLocation> getParkingMap()
	{
		return parkingMap;
	}
	

}
