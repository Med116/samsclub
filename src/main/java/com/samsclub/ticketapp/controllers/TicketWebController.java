package com.samsclub.ticketapp.controllers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.samsclub.ticketapp.util.Util;

@Component
@Controller
public class TicketWebController {

	/**
	 * The starting point of the web app
	 * 
	 * @return the index.html template
	 */
	@RequestMapping("/")
	public String home() {
		return "index";
	}

}
