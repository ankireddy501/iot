/**
 * 
 */
package com.parking.management.beans;

public class ParkingSlot {

	public ParkingSlot() {}
	
	public ParkingSlot(String name, String status, long ownerId) {
		super();
		this.name = name;
		this.status = status;
		this.ownerId = ownerId;
	}

	private String name;
	private String status;
	private long ownerId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	
	
	public long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	
}
