package main.ticket;

import java.time.LocalDateTime;

public class ParkingTicket {
	private String ticketNumber;
	private LocalDateTime IssueDate;

	public ParkingTicket(String ticketNumber, LocalDateTime issueDate) {
		this.ticketNumber = ticketNumber;
		this.IssueDate = issueDate;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}


	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}


	public LocalDateTime getIssueDate() {
		return IssueDate;
	}


	public void setIssueDate(LocalDateTime issueDate) {
		IssueDate = issueDate;
	}
	
	
	
}
