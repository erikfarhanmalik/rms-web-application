package com.mitrais.rms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RmsWebApplication {
	private static final Logger log = LoggerFactory.getLogger(RmsWebApplication.class);
	
	public static void main(String[] args) {
		log.info("Start the spting application");
		SpringApplication.run(RmsWebApplication.class, args);
	}
}
