package com.coderscampus.flightreservationapp.service;

import com.coderscampus.flightreservationapp.domain.User;
import com.coderscampus.flightreservationapp.repository.UserRepository;
import com.coderscampus.flightreservationapp.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Used by Spring Security on the Authentication Process
 * Spring Security automatically fetch the service
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username and/or password is incorrect");
        }
        return new SecurityUser(user);
	}	
}