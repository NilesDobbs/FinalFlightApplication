package com.coderscampus.flightreservationapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.coderscampus.flightreservationapp.domain.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("select u from User u"+
		   " left join fetch u.authorities "+
		   " where u.username = :username")
	User findByUsername(String username);

	User findUserByUsername(String username);

	@Query("select DISTINCT u from User u"+
			" left join fetch u.authorities")
	List<User> listAllWithAuthorities();

	//Query the user by first name or last name with paging
	Page<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
			String firstName, String lastName, Pageable pageable);

}
