package com.coderscampus.flightreservationapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArrivalDTO {
	
	@JsonProperty("airport")
	private String airportArrival;
	@JsonProperty("timezone")
	private String timezoneA;
	@JsonProperty("iata")
	private String iataA;
	@JsonProperty("icao")
	private String icaoA;
	@JsonProperty("scheduled")
	private String scheduledA;

	public ArrivalDTO() {
	}

	public ArrivalDTO(String airportArrival, String timezoneA, String iataA, String icaoA, String scheduledA) {
		this.airportArrival = airportArrival;
		this.timezoneA = timezoneA;
		this.iataA = iataA;
		this.icaoA = icaoA;
		this.scheduledA = scheduledA;
	}

	public String getAirportA() {
		return airportArrival;
	}

	public void setAirportA(String airportArrival) {
		this.airportArrival = airportArrival;
	}

	public String getTimezoneA() {
		return timezoneA;
	}

	public void setTimezoneA(String timezoneA) {
		this.timezoneA = timezoneA;
	}

	public String getIataA() {
		return iataA;
	}

	public void setIataA(String iataA) {
		this.iataA = iataA;
	}

	public String getScheduledA() {
		return scheduledA;
	}

	public void setScheduledA(String scheduledA) {
		this.scheduledA = scheduledA;
	}

	public String getAirportArrival() {
		return airportArrival;
	}

	public void setAirportArrival(String airportArrival) {
		this.airportArrival = airportArrival;
	}

	public String getIcaoA() {
		return icaoA;
	}

	public void setIcaoA(String icaoA) {
		this.icaoA = icaoA;
	}
}
