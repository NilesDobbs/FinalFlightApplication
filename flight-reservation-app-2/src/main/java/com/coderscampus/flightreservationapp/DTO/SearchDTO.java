package com.coderscampus.flightreservationapp.DTO;

import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * DTO for searches
 *
 */
public class SearchDTO {

    private String fromCity;

    private String toCity;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date date;

    private String search;

    public SearchDTO() {
        this.date = new Date();
    }

    public SearchDTO(String fromCity, String toCity, Date date) {
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.date = date;
    }

    public SearchDTO(String fromCity, String toCity, Date date, String search) {
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.date = date;
        this.search = search;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStrDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
