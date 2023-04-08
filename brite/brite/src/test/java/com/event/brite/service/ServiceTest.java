package com.event.brite.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.event.eventbrite.EventDetails.EventDetails;
import com.event.eventbrite.EventDetails.Items;
import com.event.eventbrite.exceptionHandling.UserNotFoundException;
import com.event.eventbrite.repository.EventRepository;
import com.event.eventbrite.service.EventService;

@SpringBootTest(classes = {ServiceTest.class})
@RunWith(MockitoJUnitRunner.class)
public class ServiceTest {

	@Mock
	EventRepository eventRepository;
	
	@InjectMocks
	EventService eventService;
	
	public List<EventDetails> myevents;
	
	List<Items> eventList = new ArrayList<Items>();
	
	@Test
	public void test_getAllEvents(){
		
		List<EventDetails> myevents = Arrays.asList(EventDetails.builder().id("2eb18406").type("CREATED").owner_id("123").city("PUNE").item(eventList).build(),
		EventDetails.builder().id("2eb184063").type("CREAED").owner_id("123").city("PUNE").item(eventList).build());
		
		
		when(eventRepository.findAll()).thenReturn(myevents);
		assertEquals(2,eventService.getAllEvents().size());
	}
	
	@Test
	public void test_getEventsByID() {
		
		String event_id = "2eb18406";
		EventDetails myevents = new EventDetails(event_id,"CREATED","123","PUNE",eventList);
		
		when(eventRepository.findById(event_id)).thenReturn(Optional.of(myevents));
		
		EventDetails foundEvent = eventService.getEventById(event_id);
		
		verify(eventRepository,times(1)).findById(event_id);
		assertEquals(foundEvent,myevents);
		assertThat(foundEvent.getId()).isEqualTo(event_id);
		
	}

	
	@Test
	public void test_getEventsBYCity() {
		
		
		List<EventDetails> myevents = Arrays.asList(EventDetails.builder().id("2eb18406").type("CREATED").owner_id("123").city("PUNE").item(eventList).build(),
				EventDetails.builder().id("2eb1840654").type("CREAED").owner_id("123").city("PUNE").item(eventList).build());
		
		String cityName = "PUNE";
		
		when(eventRepository.getEventByCity(cityName)).thenReturn(myevents);
		List<EventDetails> foundEvent = eventService.getEventByCity(cityName);
		
		assertEquals(foundEvent,myevents);
	}
	
	@Test
	public void test_createEvent() {
				
		EventDetails myevents = new EventDetails();
		myevents.setId("2eb18406");
		myevents.setOwner_id("23456");
		myevents.setType("CREATED");
		myevents.setCity("pune");
		myevents.setItem(eventList);
		
		
		
		when(eventRepository.save(myevents)).thenReturn(myevents);
	}
	
	@Test
	public void test_updateEvent() {
				
		String event_id = "2eb18406";
		EventDetails existevent = new EventDetails(event_id,"CREATED","123","PUNE",eventList);
		EventDetails updatedevent = new EventDetails("2eb18406","UPDATED","456","HYDERABAD",eventList);
		
		when(eventRepository.findById(event_id)).thenReturn(Optional.of(existevent));
		when(eventRepository.save(updatedevent)).thenReturn(updatedevent);
		
	}
	
	@Test
	public void test_deleteEvent() {
		
		EventDetails myevents = new EventDetails("2eb18406","CREATED","123","PUNE",eventList);
		 
		eventRepository.delete(myevents);
		verify(eventRepository,times(1)).delete(myevents);
	}
	
	
	//-------------------------------------------------------------------------------------------------------
	
	
	@Test
	public void whentest_getEventsByID_Fails() {
		String event_id = "2eb18406";
		when(eventRepository.findById(event_id)).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> {
		eventService.getEventById(event_id);
		});
	}
	
	@Test
	public void whentest_deleteByID_Fails() {
		String event_id = "2eb18406";
		when(eventRepository.findById(event_id)).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> {
		eventService.deleteEvent(event_id);
		});
	}
	
@Test
    void whentest_updateEvent_Fails() {
	String event_id = "2eb18406";
        when(eventRepository.findById(event_id)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> {
            eventService.getEventById(event_id);
        });
    }
	 

}




