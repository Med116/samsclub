package com.samsclub.ticketapp.service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.samsclub.ticketapp.data.SeatProvider;
import com.samsclub.ticketapp.models.Seat;
import com.samsclub.ticketapp.models.SeatHold;
import com.samsclub.ticketapp.util.Util;

@Component
@Service
public class TicketServiceImpl implements TicketService{

	@Override
	public int numSeatsAvailable() {
		return (int) SeatProvider.seats.stream()
				.filter(Seat::isAvailable)
				.count();
	}
	
	@Override
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		SeatHold seatHold = new SeatHold(numSeats, customerEmail);
		if(seatHold.checkIfSeatsLeft()){
			System.out.println("Seats are available");
			seatHold.holdSeats();
		}else{
			System.out.println("Seats are not available");
		}
		return seatHold;
	
	}

	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {
		Date now = new Date();
		String reservationCode = "RESERVED|" + seatHoldId  + "|" + new SimpleDateFormat("yyyy-MM-dd").format(now);
		List<Seat> reservedSeats = SeatProvider.seats.stream()
		.filter(seat-> seat.getHoldId() == seatHoldId)
		.filter(seat-> seat.getHoldExpiration().getTime() > now.getTime())
		.map(seat -> seat.reserveSeat(reservationCode, customerEmail))
		.collect(Collectors.toList());
		 return reservedSeats.size() > 0 ? reservationCode : null;
		
	}

}
