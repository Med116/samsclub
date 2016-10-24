package com.samsclub.ticketapp.models;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.samsclub.ticketapp.App;
import com.samsclub.ticketapp.data.SeatProvider;
import com.samsclub.ticketapp.service.TicketService;
import com.samsclub.ticketapp.service.TicketServiceImpl;
import com.samsclub.ticketapp.models.Seat;

/**
 * @author markdavis This class is responsible for grouping held seats together
 *         by generating random numbers to associate to held seats The random #
 *         is stored as a member variable so that upon request the user of the
 *         service can retreive it, in order to reserve the seats they held
 *
 */


public class SeatHold {

	
	private TicketService ticketService = new TicketServiceImpl();
	private String customerEmail;
	private int holdId;
	private int seatCount;
	private String errMsg;
	private boolean valid;
	private Date expirationTime;
	private List<Seat> seatsHeld;
	public  int expireSeconds = 60;

	
		
	
	/**
	 * holds seats, generates a hold id and sets it to this object, and the
	 * individual seats held, so we can look up seats by the hold id for the
	 * TicketServiceImpl::reserveSeats call
	 */
	public void holdSeats(int seconds) {
		int holdId = getRandomId();
		setSeatsHeld(SeatProvider.seats.stream().filter(Seat::isAvailable).limit(getSeatCount())
				.map(seat -> {
					seat.hold(seconds);
					seat.setHoldId(holdId);
					seat.setCustomerEmail(getCustomerEmail());
					return seat;
				}).collect(Collectors.toList()));
		setValid(true);
		setHoldId(holdId);
		setExpirationTime();
		System.out.println("SEATS LEFT IN holdSeats after stream: " + ticketService.numSeatsAvailable());
	}

	/**
	 * Checks if the user requested more seats than are available
	 * 
	 * @return `true` if there are enough seats, `false` if there are too few
	 */
	public boolean checkIfSeatsLeft() {
		System.out.println("REQUESTED SEATS:" + getSeatCount());
		System.out.println("SEATS LEFT: " + ticketService.numSeatsAvailable());
		if (getSeatCount() > ticketService.numSeatsAvailable()) {
			return false;
		}
		return true;
	}

	/**
	 * Uses java's built in random number generator
	 * 
	 * @return a random number
	 */
	private int getRandomId() {
		// TODO: get distinct list of seathold ids already issued, to make sure
		// its not already used
		// ? Should this just increment by 1 every time
		Random rand = new Random();
		return rand.nextInt(1000);
	}

	public int getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}

	public int getHoldId() {
		return holdId;
	}

	public void setHoldId(int holdId) {
		this.holdId = holdId;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Date getExpirationTime() {
		return expirationTime;
	}

	/**
	 * Sets the expiration of the hold to the future moment
	 */
	private void setExpirationTime() {
		this.expirationTime = Date.from(Instant.now().plusSeconds(expireSeconds));
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public List<Seat> getSeatsHeld() {
		return seatsHeld;
	}

	public void setSeatsHeld(List<Seat> seatsHeld) {
		this.seatsHeld = seatsHeld;
	}
	

}
