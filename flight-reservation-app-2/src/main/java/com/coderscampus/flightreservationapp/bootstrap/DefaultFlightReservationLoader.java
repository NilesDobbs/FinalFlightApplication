package com.coderscampus.flightreservationapp.bootstrap;

import com.coderscampus.flightreservationapp.domain.Authority;
import com.coderscampus.flightreservationapp.domain.FlightD;
import com.coderscampus.flightreservationapp.domain.User;
import com.coderscampus.flightreservationapp.repository.AuthorityRepository;
import com.coderscampus.flightreservationapp.repository.FlightDRepository;
import com.coderscampus.flightreservationapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/* This class is used for testing, it will load data to the database, to then be used for testing */

@Component
public class DefaultFlightReservationLoader implements CommandLineRunner {

    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private FlightDRepository flightDRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if(userRepository.count() == 0) {
            loadSecurityData();
        }
        if(flightDRepository.count() == 0) {
            loadFlightDData();
        }
    }

    private void loadFlightDData() {
        for(FlightD flightD : createFlights()) {
            flightDRepository.save(flightD);
        }
    }

    private void loadSecurityData() {

        //Some basic Authorities
        Authority adminAuthority = authorityRepository.save(new Authority("ADMIN"));
        Authority managerAuthority = authorityRepository.save(new Authority("MANAGER"));
        Authority userAuthority = authorityRepository.save(new Authority("USER"));

        userRepository.save(new User(
                "Administrator",
                "of System",
                passwordEncoder.encode("admin"),
                "admin@email.com",
                "admin",
                Set.of(adminAuthority)
        ));

        userRepository.save(new User(
                "Manager",
                "of Booking",
                passwordEncoder.encode("manager"),
                "manager@email.com",
                "manager",
                Set.of(managerAuthority)
        ));

        userRepository.save(new User(
                "User",
                "Common",
                passwordEncoder.encode("user"),
                "user@email.com",
                "user",
                Set.of(userAuthority)
        ));

    }

    private List<FlightD> createFlights() {
        FlightD flightD1 = new FlightD();
        flightD1.setOperatingAirlines("Air China LTD");
        flightD1.setDepartureAirport("Shanghai Pudong International");
        flightD1.setArrivalAirport("Beijing Capital International");
        flightD1.setDepartureTime("01:05:00+00:00");
        flightD1.setArrivalTime("03:30:00+00:00");
        flightD1.setDateOfDeparture("2022-04-11");
        flightD1.setDateOfArrival("2022-04-11");
        flightD1.setIataDeparture("PVG");
        flightD1.setIataArrival("PEK");
        flightD1.setAirlineFlightStatus("scheduled");
        flightD1.setDateOfFlight("2022-04-11");
        flightD1.setFlightNumberInfo("1082");

        FlightD flightD2 = new FlightD();
        flightD2.setOperatingAirlines("Air China LTD");
        flightD2.setDepartureAirport("Shanghai Pudong International");
        flightD2.setArrivalAirport("Heathrow");
        flightD2.setDepartureTime("01:05:00+00:00");
        flightD2.setArrivalTime("10:45:00+00:00");
        flightD2.setDateOfDeparture("2022-04-11");
        flightD2.setDateOfArrival("2022-04-11");
        flightD2.setIataDeparture("PVG");
        flightD2.setIataArrival("LHR");
        flightD2.setAirlineFlightStatus("scheduled");
        flightD2.setDateOfFlight("2022-04-11");
        flightD2.setFlightNumberInfo("611");

        FlightD flightD3 = new FlightD();
        flightD3.setOperatingAirlines("Suparna Airlines");
        flightD3.setDepartureAirport("Shanghai Pudong International");
        flightD3.setArrivalAirport("Tianjin Binhai International");
        flightD3.setDepartureTime("01:00:00+00:00");
        flightD3.setArrivalTime("03:05:00+00:00");
        flightD3.setDateOfDeparture("2022-04-11");
        flightD3.setDateOfArrival("2022-04-11");
        flightD3.setIataDeparture("PVG");
        flightD3.setIataArrival("TSN");
        flightD3.setAirlineFlightStatus("scheduled");
        flightD3.setDateOfFlight("2022-04-11");
        flightD3.setFlightNumberInfo("7403");

        FlightD flightD4 = new FlightD();
        flightD4.setOperatingAirlines("American Airlines");
        flightD4.setDepartureAirport("Darwin");
        flightD4.setArrivalAirport("Kingsford Smith");
        flightD4.setDepartureTime("00:20:00+00:00");
        flightD4.setArrivalTime("05:10:00+00:00");
        flightD4.setDateOfDeparture("2022-04-11");
        flightD4.setDateOfArrival("2022-04-11");
        flightD4.setIataDeparture("DRW");
        flightD4.setIataArrival("SYD");
        flightD4.setAirlineFlightStatus("scheduled");
        flightD4.setDateOfFlight("2022-04-11");
        flightD4.setFlightNumberInfo("7293");
        
        //returns an immutable list of the four flights to the dashboard.
        return List.of(flightD4, flightD3, flightD2, flightD1);
    }
}
