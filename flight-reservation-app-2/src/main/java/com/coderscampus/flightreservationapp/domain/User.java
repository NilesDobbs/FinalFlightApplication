package com.coderscampus.flightreservationapp.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	//First Name of the User
	@Column(name="first_name")
	private String firstName;
	//Last Name of the User
	@Column(name="last_name")
	private String lastName;
	//Password of the User
	@Column(name="password")
	private String password;
	//Email of the User
	@Column(name="email")
	private String email;
	//Username of the User
	@Column(name="username")
	private String username;
	@ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinTable(name = "user_authority",
			joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
			inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")})
	private Set<Authority> authorities;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Booking> bookings;

	public User() {

	}

	public User(String firstName, String lastName, String password,
				String email, String username, Set<Authority> authorities) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.username = username;
		this.authorities = authorities;
	}

	public User(Long id, String firstName, String lastName, String password, String email,
				String username, Set<Authority> authorities) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.username = username;
		this.authorities = authorities;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long userId) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	//Password of User
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	//Email of the User
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	//Username of the User
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public Set<Authority> getAuthority() {
		return this.authorities;
	}

	public Set<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(Set<Booking> bookings) {
		this.bookings = bookings;
	}

	/**
	 * Converts the Authorities in GrantedAuthority
	 */
	@Transient
	public Set<GrantedAuthority> getAuthorities() {
		return this.authorities.stream()
				.map(authority -> {
					return new SimpleGrantedAuthority(authority.getAuthority());
				})
				.collect(Collectors.toSet());
	}

}
