package com.au.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.au.example.model.User;
import com.au.example.repo.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan(basePackages="com.au.example")
@EnableWebMvc
public class VaadinCrawlerApplication {

	private static final Logger log = LoggerFactory.getLogger(VaadinCrawlerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(VaadinCrawlerApplication.class, args);
	}


}
