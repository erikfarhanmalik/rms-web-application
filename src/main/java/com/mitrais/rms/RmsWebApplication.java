package com.mitrais.rms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RmsWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(RmsWebApplication.class, args);
	}
}
