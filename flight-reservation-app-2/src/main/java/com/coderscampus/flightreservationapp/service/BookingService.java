package com.coderscampus.flightreservationapp.service;

import com.coderscampus.flightreservationapp.domain.Booking;
import com.coderscampus.flightreservationapp.domain.FlightD;
import com.coderscampus.flightreservationapp.domain.User;
import com.coderscampus.flightreservationapp.domain.paging.Paged;
import com.coderscampus.flightreservationapp.domain.paging.Paging;
import com.coderscampus.flightreservationapp.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.coderscampus.flightreservationapp.util.Constants.FLIGHTD_NOT_FOUD;
import static com.coderscampus.flightreservationapp.util.Constants.USER_NOT_FOUND;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    FlightDService flightDService;

    /**
     * Create the booking for the flightId, gets the user from the SecurityContext
     *
     */
    public void book(Long flightId) {
        FlightD flightD =  flightDService.findById(flightId);
        Booking booking = new Booking();
        if(flightD == null)
            throw new RuntimeException(FLIGHTD_NOT_FOUD);
        booking.setFlightD(flightD);
        booking.setUser(userService.getLoggedUser());

        bookingRepository.save(booking);
    }

    /**
     * Create the booking for the flightId and userId
     * only ADMIN can perform
     */
    public void book(Long flightId, long userId) {
        FlightD flightD =  flightDService.findById(flightId);
        Booking booking = new Booking();
        if(flightD == null)
            throw new RuntimeException(FLIGHTD_NOT_FOUD);
        User user = userService.findByUserId(userId);
        if(user == null)
            throw new RuntimeException(USER_NOT_FOUND);
        booking.setFlightD(flightD);
        booking.setUser(user);

        bookingRepository.save(booking);
    }

    /**
     * list the bookings of the logged user, gets the user from the SecurityContext
     * with paging
     *
     */
    public Paged<Booking> listBookings(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<Booking> bookings = bookingRepository.findAllByUserWithPagination(userService.getUsername(), request);
        return new Paged<>(bookings, Paging.of(bookings.getTotalPages(), pageNumber, size));
    }

    /**
     * list all bookings in the database, only ADMIN or MANAGER can perform
     *  with paging
     *
     */
    public Paged<Booking> listAllBookings(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<Booking> bookings = bookingRepository.findAllCompleteWithPagination(request);
        return new Paged<>(bookings, Paging.of(bookings.getTotalPages(), pageNumber, size));
    }

    public Booking findById(Long id) {
        return bookingRepository.findByIdComplete(id);
    }

    public boolean deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if(booking == null)
            return false;
        bookingRepository.delete(booking);
        return true;
    }
}
