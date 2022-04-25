package com.coderscampus.flightreservationapp.controller;

import com.coderscampus.flightreservationapp.DTO.SearchDTO;
import com.coderscampus.flightreservationapp.DTO.UserDTO;
import com.coderscampus.flightreservationapp.domain.Booking;
import com.coderscampus.flightreservationapp.domain.FlightD;
import com.coderscampus.flightreservationapp.domain.paging.Paged;
import com.coderscampus.flightreservationapp.security.perms.AdminPermission;
import com.coderscampus.flightreservationapp.security.perms.ManagerPermission;
import com.coderscampus.flightreservationapp.security.perms.UserPermission;
import com.coderscampus.flightreservationapp.service.BookingService;
import com.coderscampus.flightreservationapp.service.FlightDService;
import com.coderscampus.flightreservationapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * This controller handles the booking
 *
 */
@Controller
public class FlightDController {

    @Autowired
    private FlightDService flightDService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    /* Simple way to pass message to another view */
    private String message;
    private String errorMessage;

    /**
     * return the bookings of the user with paging
     *
     */
    @UserPermission
    @GetMapping("/booking/list")
    public String listBookings(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                               @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                               Model model) {
        model.addAttribute("bookings", bookingService.listBookings(pageNumber, size));

        return "booking/list-bookings";
    }

    /**
     * Just return a simple confirmation form
     *
     */
    @UserPermission
    @GetMapping("/flight/book/{flightId}")
    public String bookForm(@PathVariable("flightId") Long flightId, Model model)  {
        FlightD flightD =  flightDService.findById(flightId);

        model.addAttribute("fli", flightD);

        return "booking/book-form";
    }

    /**
     * Create the Booking and return to the list
     *
     */
    @UserPermission
    @PostMapping("/flight/book/add/{flightId}")
    public String performBook(@PathVariable("flightId") Long flightId)  {
        bookingService.book(flightId);

        return "redirect:/dashboard?booked=true";
    }

    /**
     * return all bookings with paging
     *
     */
    @ManagerPermission
    @GetMapping("/booking/all/list")
    public String listAllBookings(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                  @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                                  @RequestParam(value = "deleted", required = false, defaultValue = "false") boolean deleted,
                                  Model model) {
        if(deleted)
            message = "Book Deleted Successfully!";
        model.addAttribute("bookings", bookingService.listAllBookings(pageNumber, size));
        if(message != null) {
            model.addAttribute("message", new String(message));
            message = null;
        }
        if(errorMessage != null) {
            model.addAttribute("errorMessage", new String(errorMessage));
            errorMessage = null;
        }
        return "booking/list-all-bookings";
    }

    /**
     * return a form,on profile page
     * to admin or manager send a blank search to API
     *
     */
    @ManagerPermission
    @GetMapping("/flights/load")
    public String flightsForm(Model model) {

        return "dashboard/load-flights";
    }

    /**
     * This method list all users and handle its pagination
     *
     */
    @AdminPermission
    @GetMapping("/flight/book/user/{flightId}")
    public String getUserForm(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @PathVariable("flightId") Long flightId,
            Model model, HttpSession session)  {
        // get the search options from the session
        SearchDTO search = (SearchDTO) session.getAttribute("search");
        Paged<UserDTO> users = null;
        if(search == null) {
            search = new SearchDTO();
            users = userService.listUsers(pageNumber, size);
        }else
            users = userService.searchUsers(search.getSearch(), pageNumber, size);

        model.addAttribute("flightId", flightId);
        model.addAttribute("search", search);
        model.addAttribute("users", users);

        return "booking/select-user";
    }

    /**
     * This method handles the search for users, when admin is creating a booking for a user
     */
    @AdminPermission
    @PostMapping("/flight/book/user/{flightId}")
    public String postUserForm(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @PathVariable("flightId") Long flightId,
            @ModelAttribute("search") SearchDTO search, Model model, HttpSession session)  {
        // add the search options to the session to work with pagination
        session.setAttribute("search", search);

        model.addAttribute("flightId", flightId);
        model.addAttribute("search", search);
        model.addAttribute("users", userService.searchUsers(search.getSearch(), pageNumber, size));

        return "booking/select-user";
    }

    /**
     * return a confirmation form to admin add an book to an user
     *
     */
    @AdminPermission
    @GetMapping("/flight/book/user/{flightId}/{userID}")
    public String bookUserForm(@PathVariable("flightId") Long flightId,
            @PathVariable("userID") Long userID, Model model) {
        FlightD flightD =  flightDService.findById(flightId);
        UserDTO user = new UserDTO(userService.findByUserId(userID));
        model.addAttribute("fli", flightD);
        model.addAttribute("user", user);
        return "booking/book-user-form";
    }

    /**
     * book an flight to an user
     *
     */
    @AdminPermission
    @PostMapping("/flight/book/user/{flightId}/{userID}")
    public String processBookUserForm(@PathVariable("flightId") Long flightId,
                               @PathVariable("userID") Long userID, Model model) {
        bookingService.book(flightId, userID);
        message = "Flight Booked Successfully!";
        return "redirect:/booking/all/list";
    }

    /**
     * Just return a simple confirmation form
     *
     */
    @UserPermission
    @GetMapping("/flight/book/delete/{bookingId}")
    public String deleteBookForm(@PathVariable("bookingId") Long bookingId, Model model)  {
        Booking booking = bookingService.findById(bookingId);
        if(booking == null) {
            errorMessage = "Error: Booking not found for id: " + bookingId;
            return "redirect:/booking/all/list";
        }
        model.addAttribute("booking", booking);

        return "booking/delete-book-form";
    }

    /**
     * perform the delete Booking
     *
     */
    @UserPermission
    @PostMapping("/flight/book/delete/{bookingId}")
    public String performDeleteBook(@PathVariable("bookingId") Long bookingId)  {
        if(bookingService.deleteBooking(bookingId))
            return "redirect:/booking/all/list?deleted=true";
        else {
            errorMessage = "Error: Booking not found for id: " + bookingId;
            return "redirect:/booking/all/list";
        }
    }

    /**
     * perform the blank search to API
     *
     */
    @ManagerPermission
    @PostMapping("/flights/load")
    public String loadFlights(Model model) {
        flightDService.loadFlightsToDataBase();
        model.addAttribute("message", "Flights Loaded Successfully!");
        return "dashboard/load-flights";
    }


}
