package com.coderscampus.flightreservationapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AirlineDTO {

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("iata")
	private String iataAL;

	@JsonProperty("icao")
	private String icaoAL;

	public AirlineDTO() {
	}

	public AirlineDTO(String name, String iataAL, String icaoAL) {
		this.name = name;
		this.iataAL = iataAL;
		this.icaoAL = icaoAL;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIataAL() {
		return iataAL;
	}

	public void setIataAL(String iataAL) {
		this.iataAL = iataAL;
	}

	public String getIcaoAL() {
		return icaoAL;
	}

	public void setIcaoAL(String icaoAL) {
		this.icaoAL = icaoAL;
	}
}