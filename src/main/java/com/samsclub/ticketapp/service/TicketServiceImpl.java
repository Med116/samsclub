package com.samsclub.ticketapp.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.samsclub.ticketapp.config.Config;
import com.samsclub.ticketapp.data.SeatProvider;
import com.samsclub.ticketapp.models.Seat;
import com.samsclub.ticketapp.models.SeatHold;

@Component
@Service
public class TicketServiceImpl implements TicketService{

	public static final String EXPIRED_STATUS = "EXPIRED";
	public static final String NO_SEATS_FOUND_STATUS = "NO_SEATS_FOUND";
	private @Autowired Config config;
	

	@Override
	public int numSeatsAvailable() {
		return (int) SeatProvider.seats.stream()
				.filter(Seat::isAvailable)
				.count();
	}
	
	@Override
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		SeatHold seatHold = new SeatHold();
		seatHold.setCustomerEmail(customerEmail);
		seatHold.setSeatCount(numSeats);
		if(seatHold.checkIfSeatsLeft()){
			seatHold.holdSeats(config != null ? config.getExpireSeconds() : 60);
		}else{
			seatHold.setErrMsg(Seat.ERR_MSG);
		}
		return seatHold;
	
	}

	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {
		Date now = new Date();
		List<Seat> reservedSeatsBySeatHoldId = SeatProvider.seats.stream()
				.filter(seat-> seat.getHoldId() == seatHoldId)
				.collect(Collectors.toList());
		if(reservedSeatsBySeatHoldId.size() == 0){
			return NO_SEATS_FOUND_STATUS;
		}
		boolean expired = reservedSeatsBySeatHoldId.stream()
				.anyMatch(seat-> seat.getHoldExpiration().getTime() < now.getTime());
		String reservationCode = "RESERVED|" + seatHoldId  + "|" + new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss").format(now);
		return expired ? EXPIRED_STATUS : reservationCode;
		
	}

	
}


