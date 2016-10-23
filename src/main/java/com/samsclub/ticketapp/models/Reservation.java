package com.samsclub.ticketapp.models;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.samsclub.ticketapp.data.SeatProvider;

public class Reservation {

	public String code;
	public List<Seat> seats;
	public String email;
	public Date reservationDate;

	public Reservation( String email) {
		this.email = email;
		this.reservationDate = new Date();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		this.setSeats(SeatProvider.seats.stream()
				.filter(seat-> seat.getReservationCode() != null && seat.getReservationCode().equals(code))
				.collect(Collectors.toList()));
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}

}
