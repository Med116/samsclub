package com.samsclub.ticketapp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.samsclub.ticketapp.util.Util;

@SpringBootApplication
@ComponentScan(value="com.samsclub.*")
public class App 
{	
	public static void main( String[] args )
    {
		Util.initSeatsInMemory();
        ApplicationContext ctx = SpringApplication.run(App.class, args);
        
    }
}
