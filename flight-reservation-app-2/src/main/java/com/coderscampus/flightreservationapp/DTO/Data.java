package com.coderscampus.flightreservationapp.DTO;

import com.coderscampus.flightreservationapp.domain.FlightD;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {
	
	@JsonProperty("flight_date")
	private String flightDate;
	@JsonProperty("flight_status")
	private String flightStatus;
	@JsonProperty("departure")
	private DepartureDTO departure;
	@JsonProperty("arrival")
	private ArrivalDTO arrival;
	@JsonProperty("airline")
	private AirlineDTO airline;
	@JsonProperty("flight")
	private FlightDTO flight;

	public Data() {
	}

	public Data(String flightDate, String flightStatus, DepartureDTO departure, ArrivalDTO arrival, AirlineDTO airline, FlightDTO flight) {
		this.flightDate = flightDate;
		this.flightStatus = flightStatus;
		this.departure = departure;
		this.arrival = arrival;
		this.airline = airline;
		this.flight = flight;
	}

	public String getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}
	public String getFlightStatus() {
		return flightStatus;
	}
	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}
	public DepartureDTO getDeparture() {
		return departure;
	}
	public void setDeparture(DepartureDTO departure) {
		this.departure = departure;
	}
	public ArrivalDTO getArrival() {
		return arrival;
	}
	public void setArrival(ArrivalDTO arrival) {
		this.arrival = arrival;
	}
	public AirlineDTO getAirline() {
		return airline;
	}
	public void setAirline(AirlineDTO airline) {
		this.airline = airline;
	}

	public FlightDTO getFlight() {
		return flight;
	}

	public void setFlight(FlightDTO flight) {
		this.flight = flight;
	}

	public FlightD getFlightD() {
		FlightD flightD = new FlightD();
		flightD.setOperatingAirlines(this.getAirline().getName());
		flightD.setDepartureAirport(this.getDeparture().getAirportDeparture());
		flightD.setArrivalAirport(this.getArrival().getAirportArrival());

		//as the dates and times are String in the entity i didn't see the need to parse to type Date
		flightD.setDepartureTime(
				this.getDeparture().getScheduledD().split("T")[1]);
		flightD.setArrivalTime(
				this.getArrival().getScheduledA().split("T")[1]);
		flightD.setDateOfDeparture(
				this.getDeparture().getScheduledD().split("T")[0]);
		flightD.setDateOfArrival(
				this.getArrival().getScheduledA().split("T")[0]);

		flightD.setIataDeparture(this.getDeparture().getIataD());
		flightD.setIataArrival(this.getArrival().getIataA());
		flightD.setAirlineFlightStatus(this.flightStatus);
		flightD.setDateOfFlight(this.flightDate);

		//Concat number-iata-icao to be unique in order to update if exists in database
		flightD.setFlightUniqueKey(this.getFlight().getFlightUniqueKey());
		flightD.setFlightNumberInfo(this.getFlight().getNumberF());
		return flightD;
	}
}
