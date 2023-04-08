package com.event.eventbrite.exceptionHandling;




import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {
	
	private Date timestamp;

	private String message;
	
	private String status;

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public EventResponse(Date timestamp, String message, String status) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.status = status;
	}

	public EventResponse() {
		super();
	}
	
	
	
	
	
}
