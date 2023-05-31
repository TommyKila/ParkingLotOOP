package main.vehicle;

import main.ticket.ParkingTicket;

public class Vehicle {
	private String licenseNumber;
	private String type;
	private ParkingTicket ticket;

	public Vehicle(String licenseNumber, String type, ParkingTicket ticket) {
		this.licenseNumber = licenseNumber;
		this.type = type;
		this.ticket = ticket;
	}
	
	

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public ParkingTicket getTicket() {
		return ticket;
	}

	public String getType() {
		return type;
	}

	public Vehicle(String type) {
		this.type = type;
	}
}