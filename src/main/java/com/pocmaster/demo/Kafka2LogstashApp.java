package com.pocmaster.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Kafka2LogstashApp {

	public static void main(String[] args) {
		SpringApplication.run(Kafka2LogstashApp.class, args);
	}
}
