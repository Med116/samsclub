package com.samsclub.ticketapp.controllers;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
