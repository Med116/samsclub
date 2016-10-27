package com.samsclub.ticketapp.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Component
@Controller
public class TicketWebController {

	public @Value("${expire.seconds}") int expireSeconds;
	
	
	/**
	 * The starting point of the web app
	 * 
	 * @return the index.html template
	 */
	@RequestMapping("/")
	public ModelAndView home(Model model) {
		model.addAttribute("expireSeconds", expireSeconds);
		return new ModelAndView("index");
	}

}
