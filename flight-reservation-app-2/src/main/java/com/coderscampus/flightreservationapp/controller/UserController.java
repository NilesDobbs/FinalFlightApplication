package com.coderscampus.flightreservationapp.controller;

import com.coderscampus.flightreservationapp.DTO.UserDTO;
import com.coderscampus.flightreservationapp.exception.UsernameExistException;
import com.coderscampus.flightreservationapp.security.perms.AdminPermission;
import com.coderscampus.flightreservationapp.security.perms.UserPermission;
import com.coderscampus.flightreservationapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * This controller handles the register of new user
 * also some admin control
 */
@Controller
public class UserController {
	
	@Autowired
	private UserService userService;

	/* Simple way to pass message to another view */
	private String message;


	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new UserDTO());

		return "user/register";
	}

	@PostMapping("/register")
	public String processRegister(@Valid @ModelAttribute("user")  UserDTO user, BindingResult result, Model model) {
		if (userService.usernameExists(user)) {
			result.rejectValue("username", null, "username already exists, please type another");
		}
		if (!user.getNewPassword().equals(user.getRetypeNewPassword())){
			result.rejectValue("retypeNewPassword", null, "Passwords do not match");
		}

		if (result.hasErrors()) {
			return "user/register";
		}
		try {
			userService.createUser(user);
		} catch (UsernameExistException e) {
			//Handling Error Message
			model.addAttribute("user", user);
			model.addAttribute("errorMessage", e.getMessage());

			return "user/register";
		}

		return "redirect:/login";
	}

	/**
	 * return the user profile page
	 * where user can change password
	 * see his bookings
	 * delete account
	 */
	@UserPermission
	@GetMapping("/profile")
	public String userProfile(Model model) {

		if(message != null) {
			model.addAttribute("message", new String(message));
			message = null;
		}
		model.addAttribute("user", userService.getUser());

		return "user/user-profile";
	}

	/**
	 * Updates the user password
	 *
	 * checks if new password and retype are equal
	 * checks if new password is different form the old
	 * checks if the old password matches the one in database
	 * before updating
	 *
	 */
	@UserPermission
	@PostMapping("/profile")
	public String updateProfile(@Valid @ModelAttribute("user")  UserDTO user, BindingResult result, Model model) {
		result.recordSuppressedField("username");
		if (!user.getNewPassword().equals(user.getRetypeNewPassword())){
			result.rejectValue("retypeNewPassword", null, "passwords do not match");
		}
		if (user.getNewPassword().equals(user.getOldPassword())){
			result.rejectValue("newPassword", null, "new password cannot be the same as the old password");
		}
		if(!userService.validatePassword(user)) {
			result.rejectValue("oldPassword", null, "wrong password");
		}

		if (result.hasErrors()) {

			return "user/user-profile";
		}

		userService.updatePassword(user);

		message = "Password changed!";

		return "redirect:/profile";
	}

	/**
	 * return the user delete account confirmation page
	 *
	 */
	@UserPermission
	@GetMapping("/delete")
	public String delete(Model model) {
		model.addAttribute("user", new UserDTO());

		return "user/delete-user";
	}

	/**
	 * do the delete account process if the provide password matches
	 * if successful perform a logout
	 */
	@UserPermission
	@PostMapping("/delete")
	public String delete(UserDTO user, Model model) {
		boolean deleted = userService.deleteUser(user);
		if(!deleted) {
			model.addAttribute("user", new UserDTO());
			model.addAttribute("errorMessage", "wrong password");
			return "user/delete-user";
		}
		return "redirect:/logout";
	}

	/**
	 *
	 * This part is for admin, remove if unnecessary
	 *
	 */

	@AdminPermission
	@GetMapping("/list")
	public String listUsers(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
							@RequestParam(value = "size", required = false, defaultValue = "5") int size,
							Model model) {
		model.addAttribute("users", userService.listUsers(pageNumber, size));

		return "user/list-users";
	}

	@AdminPermission
	@GetMapping("/add")
	public String addUser(Model model) {
		model.addAttribute("user", new UserDTO());
		model.addAttribute("auths", userService.listAuthorities());

		return "user/add-users";
	}

	@AdminPermission
	@PostMapping("/add")
	public String processAddUser(@Valid @ModelAttribute("user")  UserDTO user, BindingResult result, Model model) {
		if (userService.usernameExists(user)) {
			result.rejectValue("username", null, "username already exists, please type another");
		}
		if (!user.getNewPassword().equals(user.getRetypeNewPassword())){
			result.rejectValue("retypeNewPassword", null, "Passwords do not match");
		}
		if (result.hasErrors()) {
			model.addAttribute("auths", userService.listAuthorities());
			return "user/add-users";
		}
		try {
			userService.addUser(user);
		} catch (UsernameExistException e) {
			model.addAttribute("user", user);
			model.addAttribute("auths", userService.listAuthorities());
			model.addAttribute("errorMessage", e.getMessage());

			return "user/add-users";
		}

		return "redirect:/list";
	}
}