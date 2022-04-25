package com.coderscampus.flightreservationapp.security;

import org.springframework.security.core.userdetails.UserDetails;

import com.coderscampus.flightreservationapp.domain.User;

public class SecurityUser extends User implements UserDetails {
	
		private static final long serialVersionUID = -6181706091746350146L;
		public SecurityUser() {
		}
		public SecurityUser(User user) {
			this.setId(user.getId());
			this.setUsername(user.getUsername());
			this.setEmail(getEmail());
			this.setLastName(getLastName());
			this.setPassword(user.getPassword());
			this.setAuthorities(user.getAuthority());
		}
		@Override
		public boolean isAccountNonExpired() {
			return true;
		}
		@Override
		public boolean isAccountNonLocked() {
			return true;
		}
		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}
		@Override
		public boolean isEnabled() {
			return true;
		}
}