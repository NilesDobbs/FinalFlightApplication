package com.coderscampus.flightreservationapp.service;

import com.coderscampus.flightreservationapp.DTO.UserDTO;
import com.coderscampus.flightreservationapp.domain.Authority;
import com.coderscampus.flightreservationapp.domain.User;
import com.coderscampus.flightreservationapp.domain.paging.Paged;
import com.coderscampus.flightreservationapp.domain.paging.Paging;
import com.coderscampus.flightreservationapp.exception.UsernameExistException;
import com.coderscampus.flightreservationapp.repository.AuthorityRepository;
import com.coderscampus.flightreservationapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.coderscampus.flightreservationapp.util.Constants.*;

@Service
public class UserService {

	@Autowired
	private AuthorityRepository authorityRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * For the register function
	 */
	public void createUser(UserDTO userDTO) throws UsernameExistException {
		Authority authority = authorityRepository.findByAuthority(DEFAUL_AUTHORITY);
		User user = userDTO.getUser();
		user.setId(null);
		if(authority == null) {
			authority = authorityRepository.save(new Authority(DEFAUL_AUTHORITY));
		}
		saveNewUser(user, authority);
	}

	/**
	 * For the add User of the Admin
	 */
	public void addUser(UserDTO userDTO) throws UsernameExistException {
		Authority authority = authorityRepository.findByAuthority(userDTO.getAuths());
		User user = userDTO.getUser();
		user.setId(null);

		saveNewUser(user, authority);
	}

	private void saveNewUser(User user, Authority authority) throws UsernameExistException {
		User savedUser = findByUsername(user.getUsername());
		if(savedUser != null)
			throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setAuthorities(Set.of(authority));
		userRepository.save(user);
	}

	public void updateUserProfile(User user) {
		userRepository.save(user);
	}
	public User findByUserId(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}

	/**
	 * check if exists an username in the database
	 */
	public User findByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

	/**
	 * search users when the admin is performing a booking to an user
	 * with paging
	 */
	public Paged<UserDTO> searchUsers(String search, int pageNumber, int size) {
		PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, "id"));
		Page<UserDTO> usersDTO =
		 	userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(search, search, request)
					.map(UserDTO::new);
		return new Paged<>(usersDTO, Paging.of(usersDTO.getTotalPages(), pageNumber, size));
	}
	/**
	 * list all users with paging, Admin function
	 */
	public Paged<UserDTO> listUsers(int pageNumber, int size) {
		PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, "id"));
		//Page<User> users = userRepository.findAll(request);
		Page<UserDTO> usersDTO =
				userRepository.findAll(request)
						.map(UserDTO::new);

		return new Paged<>(usersDTO, Paging.of(usersDTO.getTotalPages(), pageNumber, size));
	}

	public List<Authority> listAuthorities() {
		return authorityRepository.findAll();
	}

	/**
	 * returns the user from the Security Context
	 */
	public User getLoggedUser() {
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = findByUsername(currentPrincipalName);
		if(user == null)
			throw new RuntimeException(USERNAME_NOT_FOUND);
		return user;
	}

	/**
	 * Updates the user password
	 *
	 */
	public void updatePassword(UserDTO userDTO) {
		User user = findByUsername(userDTO.getUsername());
		String newPassword = passwordEncoder.encode(userDTO.getNewPassword());
		user.setPassword(newPassword);
		userRepository.save(user);
	}

	/**
	 * delete user
	 *
	 * check if password matches
	 * before delete all bookings
	 *I didn't use validatePassword so I don't have to do the same query over and over again
	 *
	 */
	public boolean deleteUser(UserDTO userDTO) {
		User user = findByUsername(getUsername());
		if(!passwordEncoder.matches(userDTO.getOldPassword(), user.getPassword()))
			return false;

		userRepository.delete(user);

		return true;
	}

	/**
	 *
	 * check if password matches
	 * before delete all bookings
	 *
	 */
	public boolean validatePassword(UserDTO userDTO) {
		User user = findByUsername(getUsername());
		if(passwordEncoder.matches(userDTO.getOldPassword(), user.getPassword()))
			return true;
		return false;
	}

	public boolean usernameExists(UserDTO userDTO) {
		if(findByUsername(userDTO.getUsername()) != null)
			return true;
		return false;
	}

	public UserDTO getUser() {
		return new UserDTO(findByUsername(getUsername()));
	}

	/**
	 * returns the username from the Security Context
	 */
	public String getUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
