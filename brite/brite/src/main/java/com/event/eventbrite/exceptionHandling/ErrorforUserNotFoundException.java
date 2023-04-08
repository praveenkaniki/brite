package com.event.eventbrite.exceptionHandling;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorforUserNotFoundException {

	private LocalDate timestamp;
	private String error;
	private String description;
	private String path;
	
}
