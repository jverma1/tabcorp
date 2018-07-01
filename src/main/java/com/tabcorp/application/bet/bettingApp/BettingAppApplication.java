package com.tabcorp.application.bet.bettingApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "com.tabcorp.application.bet.*" })
@EnableJpaRepositories("com.tabcorp.application.bet.dao")
@EntityScan("com.tabcorp.application.bet.model")
@EnableAutoConfiguration
public class BettingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BettingAppApplication.class, args);
	}
}
