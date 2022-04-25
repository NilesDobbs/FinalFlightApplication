package com.coderscampus.flightreservationapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CodesharedDTO {

    @JsonProperty("airline_name")
    private String airlineName;
    @JsonProperty("airline_iata")
    private String airlineIata;
    @JsonProperty("airline_icao")
    private String airlineIcao;
    @JsonProperty("flight_number")
    private String flightNumber;
    @JsonProperty("flight_iata")
    private String flightIata;
    @JsonProperty("flight_icao")
    private String flightIcao;

    public CodesharedDTO() {
    }

    public CodesharedDTO(String airlineName, String airlineIata, String airlineIcao, String flightNumber, String flightIata, String flightIcao) {
        this.airlineName = airlineName;
        this.airlineIata = airlineIata;
        this.airlineIcao = airlineIcao;
        this.flightNumber = flightNumber;
        this.flightIata = flightIata;
        this.flightIcao = flightIcao;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getAirlineIata() {
        return airlineIata;
    }

    public void setAirlineIata(String airlineIata) {
        this.airlineIata = airlineIata;
    }

    public String getAirlineIcao() {
        return airlineIcao;
    }

    public void setAirlineIcao(String airlineIcao) {
        this.airlineIcao = airlineIcao;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightIata() {
        return flightIata;
    }

    public void setFlightIata(String flightIata) {
        this.flightIata = flightIata;
    }

    public String getFlightIcao() {
        return flightIcao;
    }

    public void setFlightIcao(String flightIcao) {
        this.flightIcao = flightIcao;
    }
}
