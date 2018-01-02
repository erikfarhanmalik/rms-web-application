package com.mitrais.rms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class)
public class RmsWebApplication {
	private static final Logger log = LoggerFactory.getLogger(RmsWebApplication.class);
	
	public static void main(String[] args) {
		log.info("Start the spting application");
		SpringApplication.run(RmsWebApplication.class, args);
	}
}
