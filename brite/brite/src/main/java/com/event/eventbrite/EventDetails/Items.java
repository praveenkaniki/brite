package com.event.eventbrite.EventDetails;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Items {
	
	@NotEmpty
	@Size(min = 2, message = "Event_ID must be provided")
	private String event_id;
	
	@NotEmpty
	@Size(min = 5, message = "Event_Name should have atleast 5 characters")
	private String event_name;
	
	@NotEmpty
	@Size(min = 5, message = "Event_Title should have atleast 5 characters")
	private String event_title;
	
	@NotEmpty
	@Size(min = 5, message = "Event_Description should have atleast 5 characters")
	private String event_description;
	
	private Location location;

	public String getEvent_id() {
		return event_id;
	}

	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}

	public String getEvent_name() {
		return event_name;
	}

	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}

	public String getEvent_title() {
		return event_title;
	}

	public void setEvent_title(String event_title) {
		this.event_title = event_title;
	}

	public String getEvent_description() {
		return event_description;
	}

	public void setEvent_description(String event_description) {
		this.event_description = event_description;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Items(@NotEmpty @Size(min = 2, message = "Event_ID must be provided") String event_id,
			@NotEmpty @Size(min = 5, message = "Event_Name should have atleast 5 characters") String event_name,
			@NotEmpty @Size(min = 5, message = "Event_Title should have atleast 5 characters") String event_title,
			@NotEmpty @Size(min = 5, message = "Event_Description should have atleast 5 characters") String event_description,
			Location location) {
		super();
		this.event_id = event_id;
		this.event_name = event_name;
		this.event_title = event_title;
		this.event_description = event_description;
		this.location = location;
	}

	public Items() {
		super();
	}

	@Override
	public String toString() {
		return "Items [event_id=" + event_id + ", event_name=" + event_name + ", event_title=" + event_title
				+ ", event_description=" + event_description + ", location=" + location + "]";
	}
	
	

}
