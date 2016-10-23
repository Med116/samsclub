package com.samsclub.ticketapp.data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.samsclub.ticketapp.models.Seat;

public class SeatProvider {

	private static SeatProvider instance = null;
	public static List<Seat> seats = new ArrayList<Seat>();

	protected SeatProvider() {

	}

	public static SeatProvider getInstance() {
		if (instance == null) {
			instance = new SeatProvider();
			Pattern.compile(",").splitAsStream("a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z")
					.forEach(letter -> {
						for (int i = 1; i <= 10; i++) {
							seats.add(new Seat(letter + i));
						}
					});
			// TODO: Change to logging
			System.out.println("Done populating seats");
		}
		return instance;
	}

}
