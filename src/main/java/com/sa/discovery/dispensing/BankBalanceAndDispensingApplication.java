package com.sa.discovery.dispensing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The class BankBalanceAndDispensingApplication is main spring boot application.
 * 
 * @author dineshmetkari
 *
 */
@SpringBootApplication
@EnableSwagger2
public class BankBalanceAndDispensingApplication {
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(BankBalanceAndDispensingApplication.class, args);
	}

}
