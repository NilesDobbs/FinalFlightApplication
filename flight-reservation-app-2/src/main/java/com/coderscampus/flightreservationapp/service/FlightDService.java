package com.coderscampus.flightreservationapp.service;

import com.coderscampus.flightreservationapp.DTO.Data;
import com.coderscampus.flightreservationapp.DTO.SearchDTO;
import com.coderscampus.flightreservationapp.domain.FlightD;
import com.coderscampus.flightreservationapp.domain.paging.Paged;
import com.coderscampus.flightreservationapp.domain.paging.Paging;
import com.coderscampus.flightreservationapp.repository.FlightDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.coderscampus.flightreservationapp.util.StringUtils.isStringEmpty;

@Service
public class FlightDService {
	
	@Autowired
	private FlightDRepository flightDRepository;

	/* get the properties from the .properties file*/
	@Value("${flightlabs.access_key}")
	private String accessKeyProps;

	@Value("${flightlabs.url}")
	private String flightlabsURL;

	public List<FlightD> listAllFlights() {
		return flightDRepository.findAll();
	}

	public FlightD findById(Long flightId) {
		return flightDRepository.findById(flightId).orElse(null);
	}

	/**
	 * Fetch the flights from the external API and save to the database
	 */
	public void loadFlightsToDataBase() {
		List<Data> datas = getLocalFlights();
		for(Data data: datas) {
			FlightD flightD = data.getFlightD();
			FlightD savedFlightD = flightDRepository
					.findFlightDByFlightUniqueKey(flightD.getFlightUniqueKey());

			//if exists will update
			if(savedFlightD != null) {
				flightD.setFlightId(savedFlightD.getFlightId());
			}
			flightDRepository.save(flightD);
		}
	}

	/**
	 * Get all the flights in the database with paging
	 * used when access the dashboard
	 *
	 */
	public Paged<FlightD> getPaged(int pageNumber, int size) {
		PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, "flightId"));
		Page<FlightD> flightPage = flightDRepository.findAll(request);
		return new Paged<>(flightPage, Paging.of(flightPage.getTotalPages(), pageNumber, size));
	}

	/**
	 * Get the flights according to the search parameters in the database with paging
	 * used after the search for the API is done and when page changes
	 *
	 */
	public Paged<FlightD> searchFlightDB(SearchDTO searchDTO, int pageNumber, int size) {
		PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, "flightId"));
		Page<FlightD> flightPage = Page.empty();

		//all the combinations for the 3 params of the search
		if(searchDTO.getDate() != null) {
			if(isStringEmpty(searchDTO.getFromCity()) && isStringEmpty(searchDTO.getToCity())) {
				flightPage = flightDRepository
						.findFlightDByDateOfDeparture(
								searchDTO.getStrDate(),
								request);
			} else if(!isStringEmpty(searchDTO.getFromCity()) && !isStringEmpty(searchDTO.getToCity())) {
				flightPage = flightDRepository
						.findFlightDByIataDepartureAndIataArrivalAndDateOfDeparture(
								searchDTO.getFromCity(),
								searchDTO.getToCity(),
								searchDTO.getStrDate(),
								request);
			} else if(isStringEmpty(searchDTO.getFromCity()) && !isStringEmpty(searchDTO.getToCity())) {
				flightPage = flightDRepository
						.findFlightDByIataArrivalAndDateOfDeparture(
								searchDTO.getToCity(),
								searchDTO.getStrDate(),
								request);
			} else if(!isStringEmpty(searchDTO.getFromCity()) && isStringEmpty(searchDTO.getToCity())) {
				flightPage = flightDRepository
						.findFlightDByIataDepartureAndDateOfDeparture(
								searchDTO.getFromCity(),
								searchDTO.getStrDate(),
								request);
			}
		} else {
			if(!isStringEmpty(searchDTO.getFromCity()) && !isStringEmpty(searchDTO.getToCity())) {
				flightPage = flightDRepository
						.findFlightDByIataDepartureAndIataArrival(
								searchDTO.getFromCity(),
								searchDTO.getToCity(),
								request);
			} else if(isStringEmpty(searchDTO.getFromCity()) && !isStringEmpty(searchDTO.getToCity())) {
				flightPage = flightDRepository
						.findFlightDByIataArrival(
								searchDTO.getToCity(),
								request);
			} else if(!isStringEmpty(searchDTO.getFromCity()) && isStringEmpty(searchDTO.getToCity())) {
				flightPage = flightDRepository
						.findFlightDByIataDeparture(
								searchDTO.getFromCity(),
								request);
			}
		}
		return new Paged<>(flightPage, Paging.of(flightPage.getTotalPages(), pageNumber, size));

	}

	/**
	 * search the flights on the API according to the search parameters
	 * after persists/update all in the database,
	 * then search in the database and return with paging to the user if has more flights than the page size
	 * needs to be persisted to database to work with paging and so you don't need to consult the api all the time
	 *
	 */
	public Paged<FlightD> searchFlightAPI(SearchDTO searchDTO,int pageNumber, int size) {

		Optional iataDep = Optional.empty();
		Optional iataArr = Optional.empty();
		Optional iataDate = Optional.empty();
		if(!isStringEmpty(searchDTO.getFromCity()))
			iataDep = Optional.of(searchDTO.getFromCity());
		if(!isStringEmpty(searchDTO.getToCity()))
			iataArr = Optional.of(searchDTO.getToCity());
		if(searchDTO.getDate() != null) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			iataDate = Optional.of(dateFormat.format(searchDTO.getDate()));
		}

		String strDate = "";
		List<Data> flights = getFlights(Optional.empty(), Optional.empty(),
				iataDep, iataArr,
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), iataDate);

		for(Data data: flights) {
			FlightD flightD = data.getFlightD();
			FlightD savedFlightD = flightDRepository
					.findFlightDByFlightUniqueKey(flightD.getFlightUniqueKey());

			//if exists will update
			if(savedFlightD != null) {
				flightD.setFlightId(savedFlightD.getFlightId());
			}
			flightDRepository.save(flightD);
		}
		return searchFlightDB(searchDTO, pageNumber, size);
	}

	/**
	 * makes the call to external API, can use the filter params
	 */
	public List<Data> getFlights(Optional<String> accessKey, Optional<String> flightStatus, Optional<String> depIata,
								 Optional<String> arrIata, Optional<String> depIcao, Optional<String> arrIcao, Optional<String> airlineName, Optional<String> airlineIata,
								 Optional<String> flightNumber, Optional<String> flightIata, Optional<String> minDelayDep, Optional<String> minDelayArr, Optional<String> maxDelayDep,
								 Optional<String> maxDelayArr, Optional<String> arrScheduledTimeArr, Optional<String> arrScheduledTimeDep) {
		RestTemplate restTemplate = new RestTemplate();
		URI uri = UriComponentsBuilder.fromHttpUrl(flightlabsURL)
				.queryParam("access_key", accessKey.orElse(accessKeyProps))
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
				.build()
				.toUri();

		Data[] datas = new Data[0];

		try {
			datas = restTemplate.getForObject(uri, Data[].class);
		} catch (RestClientResponseException e) {
			e.printStackTrace();
		}

		return Arrays.asList(datas);
	}
	/**
	 * avoid the need to type Optional.empty() all the time
	 */
	private List<Data> getLocalFlights() {
		return getFlights(Optional.empty(), Optional.empty(),Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty());
	}
}
