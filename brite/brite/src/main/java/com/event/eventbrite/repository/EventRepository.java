package com.event.eventbrite.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.event.eventbrite.EventDetails.EventDetails;


@Repository
public interface EventRepository extends MongoRepository<EventDetails, String >{

	
	List<EventDetails> getEventByCity(String city);
	
		
}
