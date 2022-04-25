package com.coderscampus.flightreservationapp.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authority;

    @ManyToMany(mappedBy = "authorities")
    private Set<User> users;

    public Authority() {
    }

    public Authority(String authority) {
        this.authority = authority;
    }

    public Authority(Long id, String authority, Set<User> users) {
        this.id = id;
        this.authority = authority;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
