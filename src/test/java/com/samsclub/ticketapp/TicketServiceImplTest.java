package com.samsclub.ticketapp;

import java.util.Date;
import java.time.Instant;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.samsclub.ticketapp.data.SeatProvider;
import com.samsclub.ticketapp.models.Seat;
import com.samsclub.ticketapp.models.SeatHold;
import com.samsclub.ticketapp.service.TicketService;
import com.samsclub.ticketapp.service.TicketServiceImpl;

import junit.framework.TestCase;


public class TicketServiceImplTest extends TestCase {

	private TicketService ticketService;
	private final String EMAIL_ID = "userOfService@gmail.com";

	@Before
	public void setUp() throws Exception {
		ticketService = new TicketServiceImpl();
		// load seats beforehand
		SeatProvider.getInstance();
	}

	@Test
	public void testTicketService() {

		// should be 260 to start with
		assertEquals(260, ticketService.numSeatsAvailable());
		ticketService.findAndHoldSeats(10, EMAIL_ID);
		assertEquals(250, ticketService.numSeatsAvailable());
		ticketService.findAndHoldSeats(10, EMAIL_ID);
		assertEquals(240, ticketService.numSeatsAvailable());
		ticketService.findAndHoldSeats(20, EMAIL_ID);
		assertEquals(220, ticketService.numSeatsAvailable());

		Date expiredDate90SecondsAgo = Date.from(Instant.now().minusSeconds(90));
		SeatHold seatHold100Seats = ticketService.findAndHoldSeats(100, EMAIL_ID);
		assertEquals(120, ticketService.numSeatsAvailable());
		assertNotNull(seatHold100Seats.getHoldId());

		// this is over 260 seats, not enough seats: seathold should not give an
		// id, should also populate an error msg in the seathold object
		SeatHold seatHold200More = ticketService.findAndHoldSeats(200, EMAIL_ID);
		assertEquals(0, seatHold200More.getHoldId());
		assertEquals(120, ticketService.numSeatsAvailable());
		assertEquals(Seat.ERR_MSG, seatHold200More.getErrMsg());

		// manually updating seats' expiraton date with 30s in the past to
		// simulate expiration
		SeatProvider.seats.forEach(seat -> {
			if (seat.getHoldExpiration() != null) {
				seat.setHoldExpiration(expiredDate90SecondsAgo);
			}
		});
		assertEquals(260, ticketService.numSeatsAvailable());

		
		//reset dates to null so they are all avaiable
		SeatProvider.seats.forEach(seat -> seat.setHoldExpiration(null));
		// test reservation
		SeatHold hold = ticketService.findAndHoldSeats(100, EMAIL_ID);
		assertNull(hold.getErrMsg());
		assertEquals(160, ticketService.numSeatsAvailable());
		ticketService.reserveSeats(hold.getHoldId(), EMAIL_ID);
		// reserve right away, set to expired
		SeatProvider.seats.forEach(seat -> {
			if (seat.getHoldExpiration() != null) {
				seat.setHoldExpiration(expiredDate90SecondsAgo);
			}
		});
		// there should still be 160 even after the expiration, since they are reserved
		assertEquals(160, ticketService.numSeatsAvailable());
		

	}

}
