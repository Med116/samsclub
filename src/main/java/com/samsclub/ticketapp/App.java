package com.samsclub.ticketapp;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.samsclub.ticketapp.data.SeatProvider;

@SpringBootApplication
@ComponentScan(value="com.samsclub.*")
@PropertySource("classpath:application.properties")
public class App implements CommandLineRunner {	
	public static void main( String[] args ) {
		ApplicationContext ctx = SpringApplication.run(App.class, args);
    }

	@Override
	public void run(String... arg0) throws Exception {
		SeatProvider.getInstance();
		
	}
}
