package com.samsclub.ticketapp.controllers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samsclub.ticketapp.models.Seat;
import com.samsclub.ticketapp.models.SeatHold;
import com.samsclub.ticketapp.App;
import com.samsclub.ticketapp.data.SeatProvider;
import com.samsclub.ticketapp.service.TicketServiceImpl;

@Component
@Controller
public class TicketWebController {

	
	@Value("${testprop}") String test;
	/**
	 * The starting point of the web app
	 * 
	 * @return the index.html template
	 */
	@RequestMapping("/")
	public String home() {
		System.out.println("TST:" + test);
		return "index";
	}

}
