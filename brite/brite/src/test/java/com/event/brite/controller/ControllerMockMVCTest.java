package com.event.brite.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.event.eventbrite.EventBriteApplication;
import com.event.eventbrite.EventDetails.EventDetails;
import com.event.eventbrite.EventDetails.Items;
import com.event.eventbrite.controller.EventController;
import com.event.eventbrite.exceptionHandling.EventResponse;
import com.event.eventbrite.exceptionHandling.UserNotFoundException;
import com.event.eventbrite.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ComponentScan(basePackages = "package com.event.eventbrite")
@AutoConfigureMockMvc
@ContextConfiguration(classes = EventBriteApplication.class)
@SpringBootTest(classes = { ControllerMockMVCTest.class })
public class ControllerMockMVCTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	EventController eventController;

	@MockBean
	EventService eventService;

	@Autowired
	private ObjectMapper objectMapper;

// GETMAPPING_ALL_EVENTS	
	@Test
	public void test_When_GetAllEvents_ThenReturn_200_OK() throws Exception {

		List<EventDetails> myevents = Arrays.asList(
				EventDetails.builder().id("64250ff92bds810a7209a479").type("CREATED").owner_id("123").city("PUNE")
						.item(null).build(),
				EventDetails.builder().id("64250ff92bdb810a7209b479").type("CREATED").owner_id("123").city("Hyderabad")
						.item(null).build());

		when(eventService.getAllEvents()).thenReturn(myevents);

		mockMvc.perform(get("/v1/events").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(myevents))).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].city").value("PUNE")).andExpect(jsonPath("$[1].city").value("Hyderabad"))
				.andDo(print());
	}
	
	

	@Test
	public void test_givenInvalidRequestUrl_whenCreateEvent_thenReturn_404_NotFound() throws Exception {

		List<EventDetails> myevents = Arrays.asList(
				EventDetails.builder().id("64250ff92bds810a7209a479").type("CREATED").owner_id("123").city("PUNE")
						.item(null).build(),
				EventDetails.builder().id("64250ff92bdb810a7209b479").type("CREATED").owner_id("123").city("Hyderabad")
						.item(null).build());

		when(eventService.getAllEvents()).thenReturn(myevents);

		mockMvc.perform(get("/v1/event") // INCORRECT URL
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(myevents)))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isNotFound())
				.andDo(print());
	}

	@Test
	public void test_givenvalidEventId_whenGetEventById_thenReturnsuccess_200_OK() throws Exception {

		EventDetails event = new EventDetails("64250ff92bds810a7209a479","CREATED","123","Hyderabad",null);


		String event_id = "64250ff92bds810a7209a479";
		when(eventService.getEventById(event_id)).thenReturn(event);

		mockMvc.perform(get("/v1/events/" + "64250ff92bds810a7209a479").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(event))).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isFound()).andExpect(status().isFound())
				.andExpect(jsonPath("$.type").value("CREATED")).andExpect(jsonPath("$.city").value("Hyderabad"))
				.andDo(print());
	}

	@Test
	public void test_givenInvalidEventId_whenGetEventById_thenReturnEmpty_404_NOTFOUND() throws Exception {

		mockMvc.perform(get("/v1/events/" + "64250ff92bd0a7209a470").contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(jsonPath("$.error").value("EVENT_NOT_FOUND - 404"))
				.andExpect(jsonPath("$.description").value("EVENT WAS NOT FOUND WITH THE ID : 64250ff92bd0a7209a470"))
				.andDo(print());
	}

	@Test
	public void test_CreateEvent_thenReturnSuccessMessage_201_Created() throws Exception {

		Items item = new Items();
		item.setEvent_id("123567");
		item.setEvent_name("music concert");
		item.setEvent_title("IGNITE YOUR ENTREPRENEURIAL SPIRIT");
		item.setEvent_description("Networking Event for Small Business Owners");

		List<Items> eventList = new ArrayList<Items>();
		eventList.add(item);

		EventDetails myevents = new EventDetails();
		myevents.setId("64250ff92bdb810a7209a479");
		myevents.setCity("pune");
		myevents.setItem(eventList);

		EventResponse response = new EventResponse();
		response.setTimestamp(new Date());
		response.setMessage("NEW EVENT GOT CREATED SUCCESFULLY WITH ID : " + myevents.getId());
		response.setStatus("201 CREATED");

		 given(eventService.createEvent(myevents)).willReturn(response);
		//when(eventService.createEvent(myevents)).thenReturn(response);

		mockMvc.perform(post("/v1/events").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(myevents)))

				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	public void test_CreateEvent_withInvalidContent_thenReturn_400_BADREQUEST() throws Exception {

		Items item = new Items();
		item.setEvent_id("123567");
		item.setEvent_name("music concert");

		List<Items> eventList = new ArrayList<Items>();
		eventList.add(item);

		EventDetails myevents = new EventDetails();
		myevents.setId("64250ff92bdb810a7209a479");
		myevents.setCity("");
		myevents.setItem(eventList);

		EventResponse response = new EventResponse();
		response.setTimestamp(new Date());
		response.setMessage("NEW EVENT GOT CREATED SUCCESFULLY WITH ID : " + myevents.getId());
		response.setStatus("201 CREATED");

	 given(eventService.createEvent(myevents)).willReturn(response);

		mockMvc.perform(post("/v1/events").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(myevents)))

				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(jsonPath("$.message").value("must not be empty"))
				.andExpect(jsonPath("$.status").value("uri=/v1/events"));
	}

	@Test
	public void test_givenUpdatedEvent_thenReturnUpdatedEventObject_200_OK() throws Exception {
		Items updateditem = new Items();
		updateditem.setEvent_id("123567");
		updateditem.setEvent_name("music concert");
		updateditem.setEvent_title("IGNITE YOUR ENTREPRENEURIAL SPIRIT");
		updateditem.setEvent_description("Networking Event for Small Business Owners");

		List<Items> eventList = new ArrayList<Items>();
		eventList.add(updateditem);

		EventDetails updatemyevents = new EventDetails();
		updatemyevents.setId("64250ff92bdb810a7209a479");
		updatemyevents.setCity("pune");
		updatemyevents.setItem(eventList);

		given(eventService.updateEvent( ArgumentMatchers.any(EventDetails.class)))
				.willAnswer((invocation) -> invocation.getArgument(0));

		mockMvc.perform(put("/v1/events/event/64250ff92bdb817209a479")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatemyevents)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.city").value("pune"));

	}

	@Test
	public void test_givenUpdatedEvent_WhenNonExistingID_thenReturn_404_NotFound() throws Exception {

		Items updateditem = new Items();
		updateditem.setEvent_id("64250ff92bdb810a7209b479");
		updateditem.setEvent_name("music concert");

		List<Items> updatedeventList = new ArrayList<Items>();
		updatedeventList.add(updateditem);

		EventDetails updatemyevents = new EventDetails();
		updatemyevents.setId("64250ff92bdb810a7209a479");
		updatemyevents.setCity("pune");
		updatemyevents.setItem(updatedeventList);

		mockMvc.perform(put("/v1/events/event/{id}" , "sdfifdf").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatemyevents))).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(jsonPath("$.error").value("EVENT_NOT_FOUND - 404"))
				.andExpect(jsonPath("$.description").value("EVENT WAS NOT FOUND WITH THE ID : sdfifdf"))
				.andExpect(jsonPath("$.path").value("uri=/v1/events/event/sdfifdf"));

	}

	@Test
	public void test_givenUpdatedEvent_whenInvalidRequestBody_thenReturn400BadRequest() throws Exception {

		Items updateditem = new Items();
		updateditem.setEvent_id("64250ff92bdb810a7209b479");
		updateditem.setEvent_name("music concert");

		List<Items> updatedeventList = new ArrayList<Items>();
		updatedeventList.add(updateditem);

		EventDetails updatemyevents = new EventDetails();
		updatemyevents.setId("64250ff92bdb810a7209a479");
		updatemyevents.setCity("pune");
		updatemyevents.setItem(updatedeventList);

		mockMvc.perform(put("/v1/events/event/" + "sdfifdf").contentType(MediaType.APPLICATION_JSON))
				// .content(objectMapper.writeValueAsString(updatemyevents)))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(jsonPath("$.title").value("Bad Request"))
				.andExpect(jsonPath("$.detail").value("Failed to read request"));

	}

	@Test
	public void test_DeleteEventById_thenReturnSuccess_200_OK() throws Exception {

		Items item = new Items();
		item.setEvent_id("4124325");
		item.setEvent_name("music concert");

		List<Items> eventList = new ArrayList<Items>();
		eventList.add(item);

		EventDetails myevents = new EventDetails();
		myevents.setId("64250ff92bdb810a7209a479");
		myevents.setCity("pune");
		myevents.setItem(eventList);

		EventResponse response = new EventResponse();

		when(eventService.deleteEvent("64250ff92bdb810a7209b479")).thenReturn(response);

		this.mockMvc.perform(delete("/v1/events/{id}", myevents.getId())).andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void test_givenNonExistEventId_whenDeleteEventById_thenReturn_404_NotFound() throws Exception {

		String event_id = "64250ff92bdb810a7209a479";
		doThrow(new UserNotFoundException("404_NOT FOUND")).when(eventService).deleteEvent(event_id);

		this.mockMvc.perform(delete("/v1/events/{id}", event_id)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.error").value("EVENT_NOT_FOUND - 404"))
				.andExpect(jsonPath("$.description").value("404_NOT FOUND")).andDo(print());
	}

}
