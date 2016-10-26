package com.samsclub.ticketapp.service;


import com.samsclub.ticketapp.models.SeatHold;


public interface TicketService {
	
	/**
	 * Gets seats available, that are not reserved, and are not held
	 * @return number of free seats
	 */
	int numSeatsAvailable();
	
	/**
	 * Looks for the best seats available, in the quantity the user requests
	 * @param numSeats - number of seats to hold
	 * @param customerEmail - unique id of customer email
	 * @return SeatHold object - contains a list of seats that was held, along with the
	 * expiration time, an id of the hold, and the customer info
	 */
	SeatHold findAndHoldSeats(int numSeats, String customerEmail);
	
	/**
	 * Reserves seats for a customer, using a hold id. Will not reserve if the seathold
	 * is expired
	 * @param seatHoldId - used to look up seats that are held
	 * @param customerEmail - used to identify the unique customer
	 * @return A unique code that is provided if the seats were held successfully
	 */
	String reserveSeats(int seatHoldId, String customerEmail);
	
}
