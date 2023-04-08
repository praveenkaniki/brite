package com.event.eventbrite.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.event.eventbrite.EventDetails.EventDetails;
import com.event.eventbrite.exceptionHandling.ErrorforUserNotFoundException;
import com.event.eventbrite.exceptionHandling.EventResponse;
import com.event.eventbrite.exceptionHandling.UserNotFoundException;
import com.event.eventbrite.service.EventService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1")
public class EventController {

	Logger LOGGER = LoggerFactory.getLogger(EventController.class);

	@Autowired
	EventService eventService;

	private static final String TRACE_ID = "traceId";

	@Operation(summary = "TO GET ALL EVENTS FROM DATABASE", description = "GETTING ALL EVENT FROM THE DATABASE")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ALL EVENTS", content = {
					@Content(schema = @Schema(implementation = EventDetails.class), mediaType = APPLICATION_JSON_VALUE)
			}),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = {
					@Content(schema = @Schema(implementation = EventResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
			})
	})

	@GetMapping(value = "/events", produces = APPLICATION_JSON_VALUE)

	public ResponseEntity<List<EventDetails>> getAllEvents() {

		LOGGER.info("GETTING ALL EVENTS BY GETMAPPING METHOD " + eventService.toString());

		return ResponseEntity.status(HttpStatus.OK).header(TRACE_ID, MDC.get(TRACE_ID))
				.body(eventService.getAllEvents());
	}

	@Operation(summary = "TO GET EVENT BY USING EVENT_ID", description = "To GET EVENT FROM THE DATABASE")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "302", description = "EVENT FOUND", content = {
					@Content(schema = @Schema(implementation = EventResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
			}),
			@ApiResponse(responseCode = "404", description = "EVENT NOT FOUND", content = {
					@Content(schema = @Schema(implementation = ErrorforUserNotFoundException.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
			}),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = {
					@Content(schema = @Schema(implementation = EventResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
			})
	})

	@GetMapping(path = "/events/{id}", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<EventDetails> getEventById(@Valid @PathVariable String id) throws UserNotFoundException {
		EventDetails eventDetails = eventService.getEventById(id);
		if (eventDetails == null)
			throw new UserNotFoundException("EVENT WAS NOT FOUND WITH THE ID : " + id);

		LOGGER.info("GETTING EVENT BY ID = {}", id);
		return ResponseEntity.status(HttpStatus.FOUND).header(TRACE_ID, MDC.get(TRACE_ID)).body(eventDetails);

	}

	@Operation(summary = "TO CRETE NEW EVENT IN DATABASE", description = "TO CREATE EVENT IN DATABASE")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "EVENT CREATED SUCCESFULLY", content = {
					@Content(schema = @Schema(implementation = EventResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
			}),
			@ApiResponse(responseCode = "400", description = "INVALID REQUEST CONTENT (BAD-REQUEST)", content = {
					@Content(schema = @Schema(implementation = EventResponse.class), mediaType = APPLICATION_JSON_VALUE)
			}),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = {
					@Content(schema = @Schema(implementation = EventResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
			})
	})

	@PostMapping(value = "/events", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody EventDetails eventDetails) throws Exception {
		EventResponse evntS = eventService.createEvent(eventDetails);
		LOGGER.info("TO CREATE EVENT {}", eventService.toString());
		return ResponseEntity.status(HttpStatus.CREATED).header(TRACE_ID, MDC.get(TRACE_ID)).body(evntS);
	}

	@Operation(summary = "TO UPDATE EVENT BY PASSING EVENT_ID", description = "TO UPDATE THE EXISTING EVENT IN DATABASE")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "EVENT UPDATED SUCCESSFULLY", content = {
					@Content(schema = @Schema(implementation = EventDetails.class), mediaType = APPLICATION_JSON_VALUE)
			}),
			@ApiResponse(responseCode = "404", description = "EVENT NOT FOUND", content = {
					@Content(schema = @Schema(implementation = ErrorforUserNotFoundException.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
			}),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = {
					@Content(schema = @Schema(implementation = EventResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
			})
	})

	@PutMapping("/events/event/{id}")
	public ResponseEntity<EventDetails> updatedEvent(@Valid @PathVariable String id,
			@RequestBody EventDetails updatedEvent) {
		EventDetails eventDetails = eventService.getEventById(id);
		if (eventDetails == null)
			throw new UserNotFoundException("EVENT WAS NOT FOUND WITH THE ID : " + id);

		LOGGER.info("UPDATED EVENT BY ID = {}", updatedEvent.getId());
		return ResponseEntity.status(HttpStatus.OK).header(TRACE_ID, MDC.get(TRACE_ID))
				.body(eventService.updateEvent(updatedEvent));
	}

	@Operation(summary = "DELETE EVENT BY USING EVENT_ID", description = "TO DELETE THE EXISTING EVENT IN DATABASE")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "EVENT DELETED SUCCESSFULLY", content = {
					@Content(schema = @Schema(implementation = EventResponse.class), mediaType = APPLICATION_JSON_VALUE)
			}),
			@ApiResponse(responseCode = "404", description = "EVENT NOT FOUND", content = {
					@Content(schema = @Schema(implementation = ErrorforUserNotFoundException.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
			}),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = {
					@Content(schema = @Schema(implementation = EventResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
			})
	})

	@DeleteMapping("/events/{id}")
	public ResponseEntity<EventResponse> deleteEvent(@Valid @PathVariable String id) throws UserNotFoundException {
		LOGGER.info("DELETE EVENT BY ID = {}", id);

		return ResponseEntity.status(HttpStatus.OK).header(TRACE_ID, MDC.get(TRACE_ID))
				.body(eventService.deleteEvent(id));
	}

	@Operation(summary = "GET EVENT(S) BY CITY NAME", description = "To GET the Event Filtered By City from Database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "302", description = "EVENT FOUND", content = {
					@Content(schema = @Schema(implementation = EventDetails.class), mediaType = APPLICATION_JSON_VALUE)
			}),
			@ApiResponse(responseCode = "404", description = "EVENT NOT FOUND", content = {
					@Content(schema = @Schema(implementation = ErrorforUserNotFoundException.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
			}),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = {
					@Content(schema = @Schema(implementation = EventResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)
			})
	})

	@GetMapping("/events/cityName")
	public ResponseEntity<List<EventDetails>> getEventByCity(@RequestParam String city) {

		LOGGER.info("GETTING EVENT BY CITY = {}", city);

		return ResponseEntity.status(HttpStatus.FOUND).header(TRACE_ID, MDC.get(TRACE_ID))
				.body(eventService.getEventByCity(city));
				//hi praveen kaniki
	}

}