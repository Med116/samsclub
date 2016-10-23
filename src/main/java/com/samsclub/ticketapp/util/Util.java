package com.samsclub.ticketapp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.samsclub.ticketapp.data.SeatProvider;
import com.samsclub.ticketapp.models.Seat;

public class Util {
	public static int HOLD_SECONDS = 60; // 1 minute

	public static boolean checkIfSeatIsAvailable(String index) {

		return SeatProvider.seats.stream().filter(seat -> seat.getSeatIndex().equals(index) && seat.isAvailable())
				.findAny().isPresent();

	}

	@Deprecated
	public static String printSeatReservationInfo(String reservationId) {
		// TODO : Create JSON out of this instead
		String html = "<ol>";
		html += SeatProvider.seats.stream().filter(seat -> seat.getReservationCode().equals(reservationId))
				.map(seat -> "<li>" + seat + "</li>").collect(Collectors.joining("\n"));
		return html + "</ol>";
	}

	public static String printSeatInfo(String code) {

		return "";
	}
}
