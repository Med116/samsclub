package com.samsclub.ticketapp.models;

import java.time.Instant;
import java.util.Date;



public class Seat {

	private String seatIndex;
	private boolean available = true; // available = not reserved yet
	private boolean held = false; // held means its been chosen, but not
									// confirmed reserved yet
	private Date holdExpiration = null;
	private int holdId;
	private String reservationCode;
	private String customerEmail;
	public static final String ERR_MSG = "Sorry, not enough seats.";
	
	

	public String getSeatIndex() {
		return seatIndex;
	}

	public void setSeatIndex(String seatIndex) {
		this.seatIndex = seatIndex;
	}

	public boolean isAvailable() {
		Date now = new Date();
		
		// check if there are already reserved seats, these cannot expire
		if (getReservationCode() != null) {
			// setHeld(true);
			// setAvailable(false);
			return false;
		}
		
		// detect whether any hold has expired
		if (this.held == true && this.holdExpiration.getTime() < now.getTime()) {
			 setHeld(false);
			 setAvailable(true);
			
		}
		// if its held and has not expired yet
		if (this.holdExpiration != null && this.holdExpiration.getTime() > now.getTime()) {
			 setHeld(true);
			 setAvailable(false);
		}
		

		return available;

	}
	
	private void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isHeld() {
		return held;
	}

	private void setHeld(boolean reserved) {
		this.held = reserved;
	}

	synchronized public void hold(int seconds) {
		setHeld(true);
		setAvailable(false);
		this.setHoldExpiration(Date.from(Instant.now().plusSeconds(seconds)));
	}

	public int getHoldId() {
		return holdId;
	}

	public void setHoldId(int holdId) {
		this.holdId = holdId;
	}

	public Date getHoldExpiration() {
		return holdExpiration;
	}

	public void setHoldExpiration(Date holdExpiration) {
		this.holdExpiration = holdExpiration;
	}

	public String getReservationCode() {
		return reservationCode;
	}

	
	public void  setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}


	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	
	public Seat reserveSeat(String code, String email){
		
		setCustomerEmail(email);
		setReservationCode(code);
		
		return this;
	}

	
}
