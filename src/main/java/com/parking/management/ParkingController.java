package com.parking.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parkingmgmt")
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
			localLocation = new ParkingLocation(location.getName(), location.getLattitude(),
                    location.getLongitude(), total, service.getAvailabilityForSlots(location.getSlots()));
			locations.getLocations().add(localLocation);
		}
		
		return locations;
	}
	
	@RequestMapping(value = "/locations/{locationName}", method = RequestMethod.GET)
	public ParkingLocation getLocation(@PathVariable String locationName)
	{
        ParkingLocation location = service.getParkingMap().get(locationName);
        Integer total = location.getSlots().size() == 0 ? null : location.getSlots().size();
        ParkingLocation parkingLocation = new ParkingLocation(location.getName(), location.getLattitude(),
                location.getLongitude(), total, service.getAvailabilityForSlots(location.getSlots()));

        for (ParkingSlot slot : location.getSlots())
        {
            parkingLocation.getSlots().add(new ParkingSlot(slot.getName(), slot.getStatus(), slot.getOwnerId()));
        }

        return parkingLocation;
    }
	
	@RequestMapping(value = "/locations/{locationName}/_status", method = RequestMethod.GET)
	public ParkingLocation getLocationStatus(@PathVariable String locationName)
	{
		ParkingLocation location = service.getParkingMap().get(locationName);
		Integer total = location.getSlots().size() == 0 ? null : location.getSlots().size();
		return new ParkingLocation(location.getName(), location.getLattitude(), location.getLongitude(),
                total, service.getAvailabilityForSlots(location.getSlots()));
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

    @RequestMapping(value = "/locations/{locationName}", method = RequestMethod.POST)
    private void updateParkingLocation(@PathVariable String locationName, @RequestBody ParkingLocation parkingLocation) {
        service.manageSlots(parkingLocation);
    }
}
