package com.coderscampus.flightreservationapp.security.perms;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/*Permissions are the annotations used in each method to
indicate what type of user can perform what type of 
action on the system. In this case, those marked with 
@ManagerPermission can perform the exclusion of booking and exclude user accounts*/

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('MANAGER') or hasAuthority('ADMIN')")
public @interface ManagerPermission {
}
