package com.event.eventbrite.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.event.eventbrite.EventDetails.EventDetails;
import com.event.eventbrite.exceptionHandling.EventResponse;
import com.event.eventbrite.exceptionHandling.UserNotFoundException;
import com.event.eventbrite.repository.EventRepository;

import jakarta.validation.Valid;

@Service
public class EventService {

	@Autowired
	EventRepository eventRepository;

	public List<EventDetails> getAllEvents() {
		List<EventDetails> event = eventRepository.findAll();
		return event;
	}

	public EventDetails getEventById(String id) {

		EventDetails event = null;

		if (eventRepository.existsById(id)) {
			event = eventRepository.findById(id).get();
		}
		
		return event;

	}

	public EventResponse createEvent(EventDetails eventDetails) {

		EventDetails event = eventRepository.save(eventDetails);

		EventResponse response = new EventResponse();
		response.setTimestamp(new Date());
		response.setMessage("NEW EVENT GOT CREATED SUCCESFULLY WITH ID : " + event.getId());
		response.setStatus("201 CREATED");

		return response;
	}

	public EventDetails updateEvent(@Valid EventDetails updatedEvent) {

		return eventRepository.save(updatedEvent);

	}


	public EventResponse deleteEvent(String id) {

		EventDetails event = eventRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Event not found to update with this id : " + id));

		eventRepository.deleteById(event.getId());
		EventResponse response = new EventResponse();
		response.setTimestamp(new Date());
		response.setMessage("EVENT GOT DELETED SUCCESFULLY WITH THE ID : " + event.getId());
		response.setStatus("200 OK");
		return response;
	}

	public List<EventDetails> getEventByCity(String city) {

		List<EventDetails> event = eventRepository.getEventByCity(city);
		if (event.isEmpty()) {
			throw new UserNotFoundException("EVENT WAS NOT FOUND WITH THE CITY : " + city);
		} else {
			return event;
		}
	}

	

}
