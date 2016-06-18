package com.parking.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ParkingManagerService {

	private final static String AVAILABLE = "available";
	
	private Map<String, ParkingLocation> parkingMap = new HashMap<>();
	
	public ParkingManagerService() {}
	
	public void manageSlots(ParkingLocation location)
	{
		if(parkingMap.containsKey(location.getName()))
		{
			ParkingLocation oldLocation = parkingMap.get(location.getName());
			
			// NOTE: ignoring lattitude and longitude updates..
			if(oldLocation.getSlots() != null)
			{
				// compare and update..
				for(ParkingSlot newSlot: location.getSlots())
				{
					ParkingSlot updatableSlot = null;
					for(ParkingSlot oldSlot: oldLocation.getSlots())
					{
						if(newSlot.getName().equals(oldSlot.getName()))
						{
							updatableSlot = oldSlot;
							break;
						}
					}
					
					if(updatableSlot != null)
					{
						updatableSlot.setStatus(newSlot.getStatus());
						updatableSlot.setOwnerId(newSlot.getOwnerId());
					}
					else
					{
						oldLocation.getSlots().add(new ParkingSlot(newSlot.getName(), newSlot.getStatus(), newSlot.getOwnerId()));
					}
				}
			}
			else{
				oldLocation.getSlots().addAll(getSlots(location.getSlots()));
			}
		}
		else{
			parkingMap.put(location.getName(), location);	
		}
		
	}

	private List<ParkingSlot> getSlots(List<ParkingSlot> slots) {
		List<ParkingSlot> list = new ArrayList<>();
		
		ParkingSlot slot;
		for(ParkingSlot parkingSlot: slots)
		{
			 slot = new ParkingSlot(parkingSlot.getName(), parkingSlot.getStatus(), parkingSlot.getOwnerId());
			 list.add(slot);
		}
		
		return list;
	}
	
	public Integer getAvailabilityForSlots(List<ParkingSlot> slots)
	{
		int availablity = 0;
		
		if(slots.size() == 0) 
			return null;
		
		for(ParkingSlot slot: slots)
		{
			if(slot.getStatus().equals(AVAILABLE))
			{
				availablity++;
			}
		}
		
		return availablity;
	}

	public Map<String, ParkingLocation> getParkingMap() {
		return parkingMap;
	}
	
	{
		ParkingLocation location = new ParkingLocation("softwareag", 12.36, 15.69, 10, 3);
		location.getSlots().add(new ParkingSlot("P1", "available", 123));
		location.getSlots().add(new ParkingSlot("P2", "blocked", 456));
		parkingMap.put("softwareag", location);
	}
}
