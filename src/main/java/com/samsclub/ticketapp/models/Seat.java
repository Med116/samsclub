package com.samsclub.ticketapp.models;

import java.time.Instant;
import java.util.Date;

import com.samsclub.ticketapp.util.Util;

public class Seat {

	private String seatIndex;
	private boolean available = true; // available = not reserved yet
	private boolean held = false; // held means its been chosen, but not
									// confirmed reserved yet
	private Date holdExpiration = null;
	private int holdId;
	private String reservationCode;

	public Seat(String index) {
		this.setSeatIndex(index);
	}

	public String getSeatIndex() {
		return seatIndex;
	}

	public void setSeatIndex(String seatIndex) {
		this.seatIndex = seatIndex;
	}

	public boolean isAvailable() {
		Date now = new Date();
		// detect whether any hold has expired, and unhold seat
		if (this.held == true && this.holdExpiration.getTime() < now.getTime()) {
			// setHeld(false);
			// setAvailable(true);
			return true;
		}
		// if its held and has not expired yet, its still not available, so set
		// held==true,available==false
		if (this.holdExpiration != null && this.holdExpiration.getTime() > now.getTime()) {
			// setHeld(true);
			// setAvailable(false);
			return false;
		}
		// check if there are already reserved seats, these cannot expire
		if (getReservationCode() != null) {
			// setHeld(true);
			// setAvailable(false);
			return false;
		}

		return true;

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

	synchronized public void hold() {
		setHeld(true);
		setAvailable(false);
		this.setHoldExpiration(Date.from(Instant.now().plusSeconds(Util.HOLD_SECONDS)));
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

	public Seat setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
		return this;
	}

	@Override
	public String toString() {
		return "[ SEAT:" + this.seatIndex + "]";
	}

}
