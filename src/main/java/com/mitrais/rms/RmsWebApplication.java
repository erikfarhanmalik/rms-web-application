package com.mitrais.rms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class)
public class RmsWebApplication extends SpringBootServletInitializer {
	private static final Logger log = LoggerFactory.getLogger(RmsWebApplication.class);

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		log.info("Start the spting application");
        return application.sources(RmsWebApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RmsWebApplication.class, args);
    }
}
