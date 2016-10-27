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
	

	/**
	 * finds the count of seats left
	 * @return - count of seats (seats that are not reserved or held yet)
	 */
	@Override
	public int numSeatsAvailable() {
		return (int) SeatProvider.seats.stream()
				.filter(Seat::isAvailable)
				.count();
	}
	
	/**
	 * This finds available seats and holds them for a period of configured seconds
	 * @param numSeats - number of seats to hold
	 * @param customerEmail - unique customer id 
	 * @return SeatHold object that contains seathold id, and list of seats, and expiration time
	 */
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

	
	/**
	 * This method checks to see if there are seats with the info passed in
	 * If there are no seats with the criteria, it returns a status NO_SEATS_FOUND_STATUS
	 * If it ends up that the seats have expired, it returns the code EXPIRED_STATUS
	 * If the seats match and they are unexpired, it makes a reservation code, and then reserves the seats by 
	 * updating the data. It then will return the reservation code that is generated from email/holdId/Date
	 * @param seatHoldId - a hold id that may be expired
	 * @param email - customer email
	 * @return Status Code (successful code = reservationId)
	 */
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
		
		if(!expired){
			// do the actual reseraction
			reservedSeatsBySeatHoldId.forEach(seat -> seat.reserveSeat(reservationCode, customerEmail));
		}
		
		return expired ? EXPIRED_STATUS : reservationCode;
		
	}

	
}


