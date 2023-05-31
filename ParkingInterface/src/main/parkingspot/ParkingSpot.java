package main.parkingspot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import main.vehicle.Vehicle;

public class ParkingSpot {
	private String number;
	private Vehicle vehicle;
	private String type;
	private String floorNum;

	public ParkingSpot(String number, Vehicle vehicle, String type, String floorNum) {
		this.number = number;
		this.vehicle = vehicle;
		this.type = type;
		this.floorNum = floorNum;
	}

	public ParkingSpot(String number, String type, String floorNum) {
		this.number = number;
		this.type = type;
		this.floorNum = floorNum;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFloorNum() {
		return floorNum;
	}

	public void setFloorNum(String floorNum) {
		this.floorNum = floorNum;
	}

	public String getTicketId() {
		return vehicle.getTicket().getTicketNumber();
	}

	public String getLicenseNumber() {
		return vehicle.getLicenseNumber();
	}

	public LocalDateTime getParkedDate() {
		return vehicle.getTicket().getIssueDate();
	}

	public String getParkedDateString() {
		if(vehicle.getTicket().getIssueDate() == null) {
			return null;
		} else {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss a");
			String strg = vehicle.getTicket().getIssueDate().format(formatter);
			return strg;
			
		}
	}
}