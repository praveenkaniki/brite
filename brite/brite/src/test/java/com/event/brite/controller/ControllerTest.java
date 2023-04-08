package com.event.brite.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.event.eventbrite.EventDetails.EventDetails;
import com.event.eventbrite.EventDetails.Items;
import com.event.eventbrite.controller.EventController;
import com.event.eventbrite.exceptionHandling.EventResponse;
import com.event.eventbrite.service.EventService;

@SpringBootTest(classes = { ControllerTest.class })
public class ControllerTest {

	@Mock
	EventService eventService;

	@InjectMocks
	EventController eventController;

	List<Items> eventslist = new ArrayList<>();

	@Test
	public void test_getAllEvents() {

		List<EventDetails> myevents = Arrays.asList(
				
				EventDetails.builder().id("2eb18406").type("CREATED").owner_id("123").city("PUNE").item(eventslist)
						.build(),
				EventDetails.builder().id("2eb18406").type("CREATED").owner_id("123").city("PUNE").item(eventslist)
						.build());

		when(eventService.getAllEvents()).thenReturn(myevents);
		ResponseEntity<List<EventDetails>> response = eventController.getAllEvents();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
	}

	@Test
	public void test_getEventsByID() {
		
		
		EventDetails myevents = new EventDetails("2eb18406", "CREATED", "123", "PUNE", eventslist);

		when(eventService.getEventById(myevents.getId())).thenReturn(myevents);
		ResponseEntity<EventDetails> response = eventController.getEventById(myevents.getId());

		assertEquals(HttpStatus.FOUND, response.getStatusCode());
		assertEquals(myevents.getId(), response.getBody().getId());
	}



	@Test
	public void test_createEvent() {

		EventDetails myevents = new EventDetails("@2eb18406", "CREATED", "123", "PUNE", eventslist);
		
		EventResponse response = new EventResponse();
		response.setTimestamp(new Date());
		response.setStatus("201 CREATED");
		response.setMessage("NEW EVENT GOT CREATED SUCCESFULLY WITH ID : @2eb18406");
		
		
		when(eventService.createEvent(myevents)).thenReturn(response);
		
//		assertEquals(HttpStatus.CREATED, response.getStatus());

	}
	
	@Test
	public void test_UpdateEvent() {
		EventDetails myevents = new EventDetails("@2eb18406", "CREATED", "123", "PUNE", eventslist);
		
		when(eventService.updateEvent(myevents)).thenReturn(myevents);
		
	}


	@Test
	public void test_deleteEvent() {

		String event_id = "2eb18406";
		EventDetails myevents = new EventDetails("2eb18406", "CREATED", "123", "PUNE", eventslist);

		when(eventService.getEventById(event_id)).thenReturn(myevents);
		when(eventService.deleteEvent(event_id)).thenReturn(null);
        ResponseEntity<EventResponse> response = eventController.deleteEvent(event_id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Mockito.verify(eventService,times(1)).deleteEvent(event_id);
	}
	
	@Test
	public void test_geteventsBYCity() {

		List<EventDetails> myevents = Arrays.asList(
				EventDetails.builder().id("2eb18406").type("CREATED").owner_id("123").city("PUNE").item(eventslist)
						.build(),
				EventDetails.builder().id("2eb18406").type("CREATED").owner_id("123").city("PUNE").item(eventslist)
						.build());
		
		String cityName = "PUNE";

		when(eventService.getEventByCity(cityName)).thenReturn(myevents);
		ResponseEntity<List<EventDetails>> response = eventController.getEventByCity(cityName);

		assertEquals(HttpStatus.FOUND, response.getStatusCode());

	}

}
