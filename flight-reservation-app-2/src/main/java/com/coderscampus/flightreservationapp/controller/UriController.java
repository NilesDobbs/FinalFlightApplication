package com.coderscampus.flightreservationapp.controller;

import java.net.URI;
import java.util.Optional;

import com.coderscampus.flightreservationapp.security.perms.ManagerPermission;
import com.coderscampus.flightreservationapp.security.perms.UserPermission;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class UriController {
	
	/* The API data retrieved from FlightLabs.com, mapped only specific parts of the JSON for the dashboard. */
	
	@ManagerPermission
	@PostMapping("/home")
	public String Homepage (String accessKey, Optional<String> flightStatus, Optional<String> depIata, 
			Optional<String> arrIata, Optional<String> depIcao, Optional<String> arrIcao, Optional<String> airlineName, Optional<String> airlineIata, 
		Optional<String> flightNumber, Optional<String> flightIata, Optional<String> minDelayDep, Optional<String> minDelayArr, Optional<String> maxDelayDep, 
		Optional<String> maxDelayArr, Optional<String> arrScheduledTimeArr, Optional<String> arrScheduledTimeDep) {
		
		RestTemplate homeTemplate = new RestTemplate();
		URI uri = UriComponentsBuilder.fromHttpUrl("https://app.goflightlabs.com/flights")
													.queryParam("access_key", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiNTQ0MDQzNWIyMjA0MjBiYzZhMDZlYjQ4NDRmYzJkZjAwYjU3MmZmZDM4ZjEyMjViNjZkY2M1MWU1NjY0Y2I3M2M0ODljMDc5MDkxOTI2MDkiLCJpYXQiOjE2NDgwNzcxMzYsIm5iZiI6MTY0ODA3NzEzNiwiZXhwIjoxNjc5NjEzMTM2LCJzdWIiOiIxNjY0Iiwic2NvcGVzIjpbXX0.Jo7cMoUL-8LNcXl4xiHeg07-ZscxddUzFdArCsTgGCor-6iCgJBYAcHNTA_Lc3GxoBpDlGLgRb6C1S8FQQUM9w")
													.queryParamIfPresent("flight_status", flightStatus)
													.queryParamIfPresent("dep_iata", depIata)
													.queryParamIfPresent("arr_iata", arrIata)
													.queryParamIfPresent("dep_icao", depIcao)
													.queryParamIfPresent("arr_icao", arrIcao)
													.queryParamIfPresent("airline_name", airlineName)
													.queryParamIfPresent("airline_iata", airlineIata)
													.queryParamIfPresent("flight_number", flightNumber)
													.queryParamIfPresent("flight_iata", flightIata)
													.queryParamIfPresent("min_delay_dep", minDelayDep)
													.queryParamIfPresent("max_delay_arr", maxDelayArr)
													.queryParamIfPresent("arr_scheduled_time_arr", arrScheduledTimeArr)
													.queryParamIfPresent("arr_scheduled_time_dep", arrScheduledTimeDep)
//													.queryParam("apiKey", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiNTQ0MDQzNWIyMjA0MjBiYzZhMDZlYjQ4NDRmYzJkZjAwYjU3MmZmZDM4ZjEyMjViNjZkY2M1MWU1NjY0Y2I3M2M0ODljMDc5MDkxOTI2MDkiLCJpYXQiOjE2NDgwNzcxMzYsIm5iZiI6MTY0ODA3NzEzNiwiZXhwIjoxNjc5NjEzMTM2LCJzdWIiOiIxNjY0Iiwic2NvcGVzIjpbXX0.Jo7cMoUL-8LNcXl4xiHeg07-ZscxddUzFdArCsTgGCor-6iCgJBYAcHNTA_Lc3GxoBpDlGLgRb6C1S8FQQUM9w")
													.build()
													.toUri();


		return "home";
	}	
}
