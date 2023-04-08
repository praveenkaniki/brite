package com.event.eventbrite.EventDetails;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "eventDetails")
public class EventDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private String id;
	
	private String type;
	
	private String owner_id;
	
	@NotBlank(message = "City is mandatory*")
	private String city;
	
	@Valid
	 List<Items> item;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<Items> getItem() {
		return item;
	}

	public void setItem(List<Items> item) {
		this.item = item;
	}

	public EventDetails(String id, String type, String owner_id, @NotBlank(message = "City is mandatory*") String city,
			@Valid List<Items> item) {
		super();
		this.id = id;
		this.type = type;
		this.owner_id = owner_id;
		this.city = city;
		this.item = item;
	}

	public EventDetails() {
		super();
	}

	@Override
	public String toString() {
		return "EventDetails [id=" + id + ", type=" + type + ", owner_id=" + owner_id + ", city=" + city + ", item="
				+ item + "]";
	}
	
	

}

