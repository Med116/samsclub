package com.samsclub.ticketapp;

import java.util.Date;
import java.time.Instant;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.samsclub.ticketapp.models.SeatHold;
import com.samsclub.ticketapp.service.TicketService;
import com.samsclub.ticketapp.service.TicketServiceImpl;
import com.samsclub.ticketapp.util.Util;

import junit.framework.TestCase;


public class TicketServiceImplTest extends TestCase {
	
	private TicketService ticketService;
	private final String EMAIL_ID ="userOfService@gmail.com";
	
	@Before
	public void setUp() throws Exception{
		ticketService = new TicketServiceImpl();
		//load seats beforehand
		
		
	}
	
	@Test
	public void testHoldSeats(){
		Util.initSeatsInMemory();
		// should be 260 to start with
		assertEquals(260, Util.seatIndex.size());
		Date expiredDate90SecondsAgo = Date.from(Instant.now().minusSeconds(90));
		SeatHold seatHold100Seats = ticketService.findAndHoldSeats(100, EMAIL_ID);
		assertEquals(160, ticketService.numSeatsAvailable());
		assertNotNull(seatHold100Seats.getHoldId());
		
		// this is over 260 seats, not enough seats:  seathold should not give an id
		SeatHold seatHold200More = ticketService.findAndHoldSeats(200, EMAIL_ID);
		assertEquals(0, seatHold200More.getHoldId());
		assertEquals(160, ticketService.numSeatsAvailable());
		
		// manually updating seats' expiraton date with 30s in the past to simulate expiration
		Util.seatIndex.forEach(seat -> {
			if(seat.getHoldExpiration() != null){
				seat.setHoldExpiration(expiredDate90SecondsAgo);
			}
		});
		assertEquals(260, ticketService.numSeatsAvailable());
	}
	
	@Test
	public void testReserveSeats(){
		
	}
	
}
