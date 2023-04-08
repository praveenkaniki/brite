package com.event.eventbrite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;




@SpringBootApplication
@EnableMongoRepositories("com.event.eventbrite.repository")
@ComponentScan("com.event.*")
public class EventBriteApplication {

	public static void main(String[] args) throws Exception{
		SpringApplication.run(EventBriteApplication.class, args);
	}

	
	
}
