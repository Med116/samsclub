package com.samsclub.ticketapp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.samsclub.ticketapp.models.Seat;


public class Util {
public static int HOLD_SECONDS = 60; // 1 minute
	
	public static List<Seat> seatIndex = new ArrayList<>();
	
	public static boolean checkIfSeatIsAvailable(String index){
			
			return 	Util.seatIndex.stream()
					.filter(seat -> seat.getSeatIndex().equals(index) && seat.isAvailable() )
					.findAny()
					.isPresent();
		
	}

	public static void initSeatsInMemory() {
		// initialize static seating index ( a-z rows 10 seats per row )
    	Pattern.compile(",")
    	.splitAsStream("a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z")
    	.forEach(letter -> {
    		for(int i = 1; i <= 10; i++){
        		seatIndex.add(new Seat(letter + i));
        	}
    	});
    	// TODO: Change to logging
    	System.out.println("Done populating seats");
		
	}

	@Deprecated
	public static String printSeatReservationInfo(String reservationId) {
		//TODO : Create JSON out of this instead
		String html = "<ol>";
		html += Util.seatIndex.stream()
		.filter(seat -> seat.getReservationCode().equals(reservationId))
		.map(seat -> "<li>" + seat + "</li>")
		.collect(Collectors.joining("\n"));
		return html + "</ol>";
	}
	
	public static String printSeatInfo(String code){
		
		return "";
	}
}
