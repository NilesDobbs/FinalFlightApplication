package com.coderscampus.flightreservationapp.repository;

import com.coderscampus.flightreservationapp.domain.FlightD;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightDRepository extends JpaRepository<FlightD, Long> {

    FlightD findFlightDByFlightUniqueKey(String flightUniqueKey);

    /* methods to search with all combinations for iata Departure, Arrival and date*/
    Page<FlightD> findFlightDByIataDepartureAndIataArrivalAndDateOfDeparture(
            String iataDeparture, String iataArrival, String dateOfDeparture, Pageable pageable);

    Page<FlightD> findFlightDByIataDepartureAndDateOfDeparture(
            String iataDeparture, String dateOfDeparture, Pageable pageable);

    Page<FlightD> findFlightDByIataArrivalAndDateOfDeparture(
            String iataArrival, String dateOfDeparture, Pageable pageable);

    Page<FlightD> findFlightDByIataDepartureAndIataArrival(
            String iataDeparture, String iataArrival, Pageable pageable);

    Page<FlightD> findFlightDByIataDeparture(
            String iataDeparture, Pageable pageable);

    Page<FlightD> findFlightDByIataArrival(
            String iataArrival, Pageable pageable);

    Page<FlightD> findFlightDByDateOfDeparture(
            String dateOfDeparture, Pageable pageable);

}