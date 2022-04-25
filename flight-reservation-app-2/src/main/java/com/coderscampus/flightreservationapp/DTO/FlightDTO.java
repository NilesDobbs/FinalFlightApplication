package com.coderscampus.flightreservationapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlightDTO {

    @JsonProperty("number")
    private String numberF;
    @JsonProperty("iata")
    private String iataF;
    @JsonProperty("icao")
    private String icaoF;
    @JsonProperty("codeshared")
    private CodesharedDTO codeshared;

    public FlightDTO() {
    }

    public FlightDTO(String numberF, String iataF, String icaoF, CodesharedDTO codeshared) {
        this.numberF = numberF;
        this.iataF = iataF;
        this.icaoF = icaoF;
        this.codeshared = codeshared;
    }

    public String getNumberF() {
        return numberF;
    }

    public String getFlightUniqueKey() {
        return numberF + "-" + iataF + "-" + icaoF;
    }

    public void setNumberF(String numberF) {
        this.numberF = numberF;
    }

    public String getIataF() {
        return iataF;
    }

    public void setIataF(String iataF) {
        this.iataF = iataF;
    }

    public String getIcaoF() {
        return icaoF;
    }

    public void setIcaoF(String icaoF) {
        this.icaoF = icaoF;
    }

    public CodesharedDTO getCodeshared() {
        return codeshared;
    }

    public void setCodeshared(CodesharedDTO codeshared) {
        this.codeshared = codeshared;
    }
}
