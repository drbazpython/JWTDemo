package com.drbaz.auth.demo.auth


import com.drbaz.auth.demo.service.UserService
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import com.drbaz.auth.demo.auth.JWTAuthenticationFilter

@EnableWebSecurity
class WebSecurityConfig(private val userService: UserService,
                        private val passwordEncoder: BCryptPasswordEncoder
) : WebSecurityConfigurerAdapter() {

	override fun configure(http: HttpSecurity) {
		http.cors().and().csrf().disable()
			.authorizeRequests()
			// Allow anyone to access /register and /login end points.
			.antMatchers("/register").permitAll()
			.antMatchers("/login").permitAll()
			// All other requests require a user to be authenticated
			.anyRequest().authenticated()
			.and()
			// Configure login so it uses our created login page and redirects to the dashboard page upon
			// a successful login.
			.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/login")
			.defaultSuccessUrl("/dashboard", true)
			.and()
			// Register our authentication and authorization filters with spring security.
			.addFilter(JWTAuthenticationFilter(authenticationManager()))
			.addFilter(JWTAuthorizationFilter(authenticationManager()))
			// Disable session creation for spring security as the JWTs will be holding session information.
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	}

	override fun configure(auth: AuthenticationManagerBuilder) {
		// Register the user service with spring security, so it knows how to load user information.
		// Also let spring know what password encoder to use.
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder)
	}
}