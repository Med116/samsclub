package com.samsclub.ticketapp.controllers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samsclub.ticketapp.models.Seat;
import com.samsclub.ticketapp.models.SeatHold;
import com.samsclub.ticketapp.App;
import com.samsclub.ticketapp.service.TicketServiceImpl;
import com.samsclub.ticketapp.util.Util;

@Component
@RestController
public class TicketServiceController {
		
	@Autowired
	private TicketServiceImpl ticketService;
	
	 @RequestMapping("/")
	    public String index() {
		 	return "<html><body> <h1> Reserve Seat</h1> <form method=\"POST\"action=\"/seats/find-and-hold/\"><p>Num Seats : <input name=\"numSeats\" type=\"text\"/></p><p> Email: <input name=\"email\" type=\"text\"/></p><input type=\"submit\"></form></body></html>";
	    }
	 
	 @RequestMapping("/seats/all")
	    public String allSeats() {       
		 	return Util.seatIndex.stream()
		 			.map(Seat::getSeatIndex)
		 			.collect(Collectors.joining(","));
	    }
	 
	 @RequestMapping("/seats/available/")
	    public String availableSeats() {
		 	return "Seats Available: " + ticketService.numSeatsAvailable();
	    }
	 
	 @RequestMapping(value="/seats/find-and-hold/", method=RequestMethod.POST)
	 public String findAndHoldSearts(@RequestParam("numSeats") String numSeats,  @RequestParam("email") String email ){
		 SeatHold hold = ticketService.findAndHoldSeats(Integer.parseInt(numSeats), email);
		 if(hold.isValid()){
			return "Seat(s) reserved with id :" + hold.getHoldId() + ". You have " + Util.HOLD_SECONDS + " seconds to reserve!";
		 }else{
			return "Error: No more seats available";
		 }
	 }
	 
	 @RequestMapping(value="/seats/reserve/", method=RequestMethod.POST)
	 public String reserveSeat(@RequestParam("seatHoldId") String seatHoldId, @RequestParam("email") String email){
		 String reservationId = ticketService.reserveSeats(Integer.parseInt(seatHoldId), email);
		 if(reservationId == null){
			 return "Problem with seat reservation!";
		 }else{
			 return "Success, your seat reservation is : "+ reservationId + " \n" + Util.printSeatInfo(reservationId);
		 }
		 
	 }
	

}
