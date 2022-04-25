package com.coderscampus.flightreservationapp.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/* This controller will return the index page and the login page
 * The authentication processes is handled by SpringSecurity
 */
@Controller
public class LoginController implements ErrorController {
	
	//This error will specifically happen is the user tries to register for an account, but an error occurs.
	@GetMapping({"/", "/index"})
	public String home(@RequestParam(value = "error", required = false, defaultValue = "false") boolean error, Model model) {
		if(error)
			model.addAttribute("errorMessage", "An error occurred. Please try again!");
		return "index";
	}

	 @GetMapping("/login")
	 public String login() {

		return "login";
	 }
	 
	//They will be redirected back to the home page if the registration fails.
	@PostMapping("/error")
	public String error() {
		return "redirect:/index?error=true";
	}

}
