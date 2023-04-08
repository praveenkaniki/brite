package com.event.eventbrite.EventDetails;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {
	
	private String address;
	
	private String city_name;
	
	private String state_name;
	
	private String country_name;
	
	private String postal_code;
	@Size(min = 10 , max =10 , message = "Phone_Number shouldbe 10 digits")
	private String phone_number;
	
	private String company_name;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	public String getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public Location(String address, String city_name, String state_name, String country_name, String postal_code,
			@Size(min = 10, max = 10, message = "Phone_Number shouldbe 10 digits") String phone_number,
			String company_name) {
		super();
		this.address = address;
		this.city_name = city_name;
		this.state_name = state_name;
		this.country_name = country_name;
		this.postal_code = postal_code;
		this.phone_number = phone_number;
		this.company_name = company_name;
	}

	public Location() {
		super();
	}

	@Override
	public String toString() {
		return "Location [address=" + address + ", city_name=" + city_name + ", state_name=" + state_name
				+ ", country_name=" + country_name + ", postal_code=" + postal_code + ", phone_number=" + phone_number
				+ ", company_name=" + company_name + "]";
	}
	
}