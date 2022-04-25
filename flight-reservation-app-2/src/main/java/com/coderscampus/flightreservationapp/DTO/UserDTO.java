package com.coderscampus.flightreservationapp.DTO;

import com.coderscampus.flightreservationapp.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * DTO class that contains the validations for the user
 * and to avoid polluting the domain class
 * and help not to send sensitive information to the browser
 */
public class UserDTO {

    private Long id;

    @NotEmpty(message = "User's last first name cannot be empty.")
    @Size(min = 2, max = 250, message = "Size must be between 2 and 250")
    private String firstName;

    @NotEmpty(message = "User's last name cannot be empty.")
    @Size(min = 2, max = 250, message = "Size must be between 2 and 250")
    private String lastName;

    @NotEmpty(message = "User's email cannot be empty.")
    @Email(message = "Please enter a valid email address.")
    @Size(max = 250, message = "Size must be maximum of 250")
    private String email;

    //@UniqueUsername
    @NotEmpty(message = "Username  cannot be empty.")
    @Size(min = 2, max = 250, message = "Size must be between 2 and 250")
    private String username;
    private String auths;

    @NotEmpty(message = "User's new password cannot be empty.")
    @Size(min = 2, max = 250, message = "Size must be between 8 and 250")
    private String newPassword;

    @Size(min = 2, max = 250, message = "Size must be between 8 and 250")
    private String oldPassword;

    @Size(min = 2, max = 250, message = "Size must be between 8 and 250")
    private String retypeNewPassword;

    public UserDTO() {
    }

    public UserDTO(Long id, String firstName, String lastName, String email, String username,
                   String auths, String newPassword, String oldPassword, String retypeNewPassword) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.auths = auths;
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
        this.retypeNewPassword = retypeNewPassword;
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.auths = user.getAuthority()
                .stream().map(e -> " | " + e.getAuthority())
                .reduce("", String::concat).substring(2);
    }

    public User getUser() {
        return new User(this.firstName, this.lastName, this.newPassword,
                this.email, this.username, null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuths() {
        return auths;
    }

    public void setAuths(String auths) {
        this.auths = auths;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getRetypeNewPassword() {
        return retypeNewPassword;
    }

    public void setRetypeNewPassword(String retypeNewPassword) {
        this.retypeNewPassword = retypeNewPassword;
    }
}
