package com.coderscampus.flightreservationapp.repository;

import com.coderscampus.flightreservationapp.domain.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b"+
            " join b.flightD"+
            " where b.user.username = :username")
    Page<Booking> findAllByUserWithPagination(@Param("username") String username, Pageable pageable);

    @Query("select b from Booking b"+
            " join b.flightD "+
            " join b.user ")
    Page<Booking> findAllCompleteWithPagination(Pageable pageable);

    @Query("select b from Booking b"+
            " join b.flightD"+
            " join b.user"+
            " where b.id = :id")
    Booking findByIdComplete(@Param("id") Long id);
}
