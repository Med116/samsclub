package com.samsclub.ticketapp;

import static org.junit.Assert.*;

import java.util.Date;
import java.time.Instant;

import org.junit.Test;

import com.samsclub.ticketapp.models.Seat;

public class SeatTest {

	@Test
	public void testIsAvailable() {
		Seat seat = new Seat();
		boolean shouldBeTrue = seat.isAvailable();
		assertTrue(shouldBeTrue);
		seat.hold(30);
		assertFalse(seat.isAvailable());
		// make is expire
		seat.setHoldExpiration(Date.from(Instant.now().minusSeconds(60)));
		assertEquals(true, seat.isAvailable());
		
		
	}
	
	@Test
	public void testReservedSeat(){
		Seat reservedSeat = new Seat();
		assertEquals(true, reservedSeat.isAvailable());
		reservedSeat.reserveSeat("TEST CODE", "email@address.com");
		assertEquals(false, reservedSeat.isAvailable());
		
	}

}
