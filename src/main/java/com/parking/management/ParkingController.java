package com.parking.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
(value="/parkingmanagement")
public class ParkingController {

	 @Autowired
	 ParkingManagerService service;
	
	@RequestMapping(value = "/locations", method = RequestMethod.GET)
	public ParkingLocations getAllLocation()
	{
		ParkingLocations locations = new ParkingLocations();
		
		ParkingLocation localLocation;
		for(ParkingLocation location: service.getParkingMap().values())
		{
			Integer total = location.getSlots().size() == 0 ? null : location.getSlots().size();
			localLocation = new ParkingLocation(location.getName(), location.getLattitude(), location.getLongitude(), total, service.getAvailabilityForSlots(location.getSlots()));
			locations.getLocations().add(localLocation);
		}
		
		return locations;
	}
	
	@RequestMapping(value = "/locations/{locationName}", method = RequestMethod.GET)
	public ParkingLocation getLocation(@PathVariable String locationName)
	{
		return service.getParkingMap().get(locationName);
	}
	
	@RequestMapping(value = "/locations/{locationName}/_status", method = RequestMethod.GET)
	public ParkingLocation getLocationStatus(@PathVariable String locationName)
	{
		ParkingLocation location = service.getParkingMap().get(locationName);
		Integer total = location.getSlots().size() == 0 ? null : location.getSlots().size();
		return new ParkingLocation(location.getName(), location.getLattitude(), location.getLongitude(), total, service.getAvailabilityForSlots(location.getSlots()));
	}
	
	@RequestMapping(value = "/locations/{locationName}/{slotName}/_status", method = RequestMethod.GET)
	public ParkingSlot getLocationSlotStatus(@PathVariable String locationName, @PathVariable String slotName)
	{
		for(ParkingSlot slot: service.getParkingMap().get(locationName).getSlots())
		{
			if(slot.getName().equalsIgnoreCase(slotName))
			{
				return slot;
			}
		}
		
		return null;
	}
}
