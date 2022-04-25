package com.coderscampus.flightreservationapp.repository;

import com.coderscampus.flightreservationapp.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    public Authority findByAuthority(String authority);

}
