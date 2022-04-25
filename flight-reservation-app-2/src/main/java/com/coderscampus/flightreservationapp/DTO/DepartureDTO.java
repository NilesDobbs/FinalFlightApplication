package com.coderscampus.flightreservationapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DepartureDTO {
	@JsonProperty("airport")
	private String airportDeparture;
	@JsonProperty("timezone")
	private String timezoneD;
	@JsonProperty("iata")
	private String iataD;
	@JsonProperty("icao")
	private String icaoD;
	@JsonProperty("scheduled")
	private String scheduledD;

	public DepartureDTO() {
		
	}

	public DepartureDTO(String airportDeparture, String timezoneD, String iataD, String icaoD, String scheduledD) {
		this.airportDeparture = airportDeparture;
		this.timezoneD = timezoneD;
		this.iataD = iataD;
		this.icaoD = icaoD;
		this.scheduledD = scheduledD;
	}

	public String getAirportDeparture() {
		return airportDeparture;
	}
	public void setAirportDeparture(String airportDeparture) {
		this.airportDeparture = airportDeparture;
	}
	public String getTimezoneD() {
		return timezoneD;
	}
	public void setTimezoneD(String timezoneD) {
		this.timezoneD = timezoneD;
	}
	public String getIataD() {
		return iataD;
	}
	public void setIataD(String iataD) {
		this.iataD = iataD;
	}
	public String getScheduledD() {
		return scheduledD;
	}
	public void setScheduledD(String scheduledD) {
		this.scheduledD = scheduledD;
	}

	public String getIcaoD() {
		return icaoD;
	}

	public void setIcaoD(String icaoD) {
		this.icaoD = icaoD;
	}
}