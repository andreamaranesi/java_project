package com.instagram.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
@ComponentScan("com.instagram.api.*")
public class InstagramBoot {

	public static void main(String[] args) {
		SpringApplication.run(InstagramBoot.class, args);
	}

}
