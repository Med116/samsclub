package com.samsclub.ticketapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class Config implements EnvironmentAware {

	private int expireSeconds;
	private Environment env;

	@Autowired
	public Config(Environment env) {
		this.setExpireSeconds(Integer.parseInt(env.getProperty("expire.seconds")));
	}

	@Override
	public void setEnvironment(Environment env) {

		this.env = env;
	}

	public int getExpireSeconds() {
		return expireSeconds;
	}

	public void setExpireSeconds(int expireSeconds) {
		this.expireSeconds = expireSeconds;
	}

}
