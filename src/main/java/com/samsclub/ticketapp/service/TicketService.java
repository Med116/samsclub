package com.samsclub.ticketapp.service;


import com.samsclub.ticketapp.models.SeatHold;


public interface TicketService {

	int numSeatsAvailable();
	
	SeatHold findAndHoldSeats(int numSeats, String customerEmail);
	
	String reserveSeats(int seatHoldId, String customerEmail);
}
