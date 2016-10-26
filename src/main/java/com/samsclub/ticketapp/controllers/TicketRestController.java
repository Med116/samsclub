package com.samsclub.ticketapp.controllers;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samsclub.ticketapp.config.Config;
import com.samsclub.ticketapp.data.SeatProvider;
import com.samsclub.ticketapp.models.Reservation;
import com.samsclub.ticketapp.models.Seat;
import com.samsclub.ticketapp.models.SeatHold;
import com.samsclub.ticketapp.models.SeatsAvailable;
import com.samsclub.ticketapp.service.TicketServiceImpl;

@Component
@RestController
public class TicketRestController {

	@Autowired
	private TicketServiceImpl ticketService;
	
	@Autowired
	private Config config;

	/**
	 * Attempts to holds seats by checking availability
	 * 
	 * @param numSeats
	 *            requested seats number
	 * @param email
	 *            unique customer id
	 * @return SeatHold object, can contain seats list and hold id or an
	 *         errorMsg if there were no more available seats.
	 */
	@RequestMapping(value = "/seats/find-and-hold/", method = RequestMethod.POST)
	public SeatHold findAndHoldSearts(@RequestParam("numSeats") String numSeats, @RequestParam("email") String email) {
		return ticketService.findAndHoldSeats(Integer.parseInt(numSeats), email);
	}

	/**
	 * Called to reserve seats with a seat hold id thats not expired
	 * 
	 * @param seatHoldId
	 *            this may be expired, the ticket service checks
	 * @param email
	 *            this must match the seats held, ticket service checks
	 * @return reservaion object with code and seatlist, customer details
	 */
	@RequestMapping(value = "/seats/reserve/", method = RequestMethod.POST)
	public Reservation reserveSeats(@RequestParam("seatHoldId") String seatHoldId, @RequestParam("email") String email) {
		String reservationId = ticketService.reserveSeats(Integer.parseInt(seatHoldId), email);
		Reservation reservation = new Reservation(email);
		if (reservationId != null) {
			reservation.setCode(reservationId);
		} else {
			System.out.println("ERR: null reservation id");
		}
		return reservation;
	}

	/**
	 * 
	 * @return A simple object containing the number of seats available
	 */
	@RequestMapping(value = "/seats/available/", produces = "application/json")
	public SeatsAvailable availableSeats() {
		
		SeatsAvailable seatsAvailable = new SeatsAvailable(ticketService.numSeatsAvailable());
		
		return seatsAvailable;
	}

	/**
	 * @return All Seats, for constructing the seats markup
	 */
	@RequestMapping("/seats/all/")
	public List<Seat> allSeats() {
		return SeatProvider.seats;
	}
	
	

}
