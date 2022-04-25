package com.coderscampus.flightreservationapp.controller;

import com.coderscampus.flightreservationapp.DTO.SearchDTO;
import com.coderscampus.flightreservationapp.domain.FlightD;
import com.coderscampus.flightreservationapp.domain.paging.Paged;
import com.coderscampus.flightreservationapp.security.perms.UserPermission;
import com.coderscampus.flightreservationapp.service.FlightDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * This controller return the dashboard page
 * and search flights on the external API
 *
 */
@Controller
public class DashboardController {

    @Autowired
    private FlightDService flightDService;

    /* Simple way to pass message to another view */
    private String message;
    private String errorMessage;

    /**
     *
     * This method handles the access to the dashboard page and its pagination
     *
     */
    @UserPermission
    @GetMapping("/dashboard")
    public String dashboardPaged(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                 @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                                 @RequestParam(value = "booked", required = false, defaultValue = "false") boolean booked,
                                 Model model, HttpSession session) {

        if(booked)
            message = "Flight Booked Successfully!";

        // get the search options from the session
        SearchDTO search = (SearchDTO) session.getAttribute("search");
        Paged<FlightD> flights = null;
        // if has no search options get from the database
        if(search == null) {
            search = new SearchDTO();
            flights = flightDService.getPaged(pageNumber, size);
            //TODO if needed can use this options to send a blank search to the API
            // will consume the API quota for every access on the dashboard page
            //Paged<FlightD> flights = flightDService.searchFlightAPI(search, pageNumber, size);
        } else {
            // if has search options mean it came from pagination just get page from the database
            flights = flightDService.searchFlightDB(search, pageNumber, size);
        }

        model.addAttribute("flights", flights);
        model.addAttribute("search", search);
        if(message != null) {
            model.addAttribute("message", new String(message));
            message = null;
        }
        if(errorMessage != null) {
            model.addAttribute("errorMessage", new String(errorMessage));
            errorMessage = null;
        }
        return "dashboard/dashboard";
    }

    /**
     *
     * This method handles the search for flights
     *
     */
    @UserPermission
    @PostMapping("/dashboard/flights/search")
    public String flightsSearch(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                                @ModelAttribute("search") SearchDTO search, Model model, HttpSession session) {
        // add the search options to the session to work with pagination
        session.setAttribute("search", search);
        Paged<FlightD> flights = flightDService.searchFlightAPI(search, pageNumber, size);
        model.addAttribute("flights", flights);
        if(message != null) {
            model.addAttribute("message", new String(message));
            message = null;
        }
        if(errorMessage != null) {
            model.addAttribute("errorMessage", new String(errorMessage));
            errorMessage = null;
        }
        return "dashboard/dashboard";
    }

}
