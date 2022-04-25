package com.coderscampus.flightreservationapp.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="FlightD")
public class FlightD {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long flightId;
	private String operatingAirlines;
	private String departureAirport;
	private String arrivalAirport;
	private String departureTime;
	private String arrivalTime;
	private String dateOfDeparture;
	private String dateOfArrival;
	private String iataDeparture;
	private String iataArrival;
	private String airlineFlightStatus;
	private String dateOfFlight;
	private String flightNumberInfo;
	
	private String flightUniqueKey; //This field will compare and update flights in the database

	@OneToMany(mappedBy = "flightD", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Booking> bookings;

	@Transient
	private String label;


	public Long getFlightId() {
		return flightId;
	}
	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}
	public String getOperatingAirlines() {
		return operatingAirlines;
	}
	public void setOperatingAirlines(String operatingAirlines) {
		this.operatingAirlines = operatingAirlines;
	}
	public String getDepartureAirport() {
		return departureAirport;
	}
	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
	}
	public String getArrivalAirport() {
		return arrivalAirport;
	}
	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getDateOfDeparture() {
		return dateOfDeparture;
	}
	public void setDateOfDeparture(String dateOfDeparture) {
		this.dateOfDeparture = dateOfDeparture;
	}
	public String getDateOfArrival() {
		return dateOfArrival;
	}
	public void setDateOfArrival(String dateOfArrival) {
		this.dateOfArrival = dateOfArrival;
	}
	public String getIataDeparture() {
		return iataDeparture;
	}
	public void setIataDeparture(String iataDeparture) {
		this.iataDeparture = iataDeparture;
	}
	public String getIataArrival() {
		return iataArrival;
	}
	public void setIataArrival(String iataArrival) {
		this.iataArrival = iataArrival;
	}
	public String getAirlineFlightStatus() {
		return airlineFlightStatus;
	}
	public void setAirlineFlightStatus(String airlineFlightStatus) {
		this.airlineFlightStatus = airlineFlightStatus;
	}
	public String getDateOfFlight() {
		return dateOfFlight;
	}
	public void setDateOfFlight(String dateOfFlight) {
		this.dateOfFlight = dateOfFlight;
	}
	public String getFlightNumberInfo() {
		return flightNumberInfo;
	}
	public void setFlightNumberInfo(String flightNumberInfo) {
		this.flightNumberInfo = flightNumberInfo;
	}

	public String getFlightUniqueKey() {
		return flightUniqueKey;
	}

	public void setFlightUniqueKey(String flightUniqueKey) {
		this.flightUniqueKey = flightUniqueKey;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}