package com.coderscampus.flightreservationapp.security;

import com.coderscampus.flightreservationapp.security.filter.AutheticatedFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/* lets you control access at the method level adding more flexibility */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private AutheticatedFilter autheticatedFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// add the filter to the spring security filter chain
		http.addFilterBefore(autheticatedFilter, UsernamePasswordAuthenticationFilter.class);

		http
				.authorizeRequests(authorize -> { //permit the public urls
					authorize.antMatchers("/resources/**").permitAll();
					authorize.antMatchers("/", "/login", "/index", "/register", "/error").permitAll();
				}).authorizeRequests().requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.and().authorizeRequests()
				.anyRequest().authenticated() //anything else is gonna need authentication
				.and()
				.formLogin( //spring security is gonna handle the login an logout process
						loginConfigurer -> { // login configuration
							loginConfigurer.loginProcessingUrl("/login")// from LoginController
									.loginPage("/login").permitAll()
									.successForwardUrl("/dashboard")
									.defaultSuccessUrl("/dashboard")
									.failureUrl("/login?error");

						}
				)
				.logout(logoutConfigurer -> {// logout configuration
						logoutConfigurer //spring security expects a post for logout, change to get
								.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
								.logoutSuccessUrl("/?logout").permitAll()
								.logoutSuccessUrl("/");
				})
				.httpBasic();
	}

}