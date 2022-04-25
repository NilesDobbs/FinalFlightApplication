package com.coderscampus.flightreservationapp.domain;

import javax.persistence.*;

/**
 * I decide to add the booking entity, basically does the same as a many-to-many relationship
 * But it is better represented with an entity, it is more visible in the model
 * And makes it easier to add information to the booking such as voucher, booking date ...
 */
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private FlightD flightD;

    public Booking() {
    }
    
    //I will likely not need this.
    public Booking(Long id, User user, FlightD flightD) {
        this.id = id;
        this.user = user;
        this.flightD = flightD;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FlightD getFlightD() {
        return flightD;
    }

    public void setFlightD(FlightD flightD) {
        this.flightD = flightD;
    }
}
