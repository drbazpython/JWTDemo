package com.drbaz.auth.demo.auth

import org.springframework.boot.web.servlet.server.Session
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.Elements.JWT
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import javax.servlet.http.Cookie

const val EXPIRATION_TIME = 1209600000 // 2 weeks in ms
const val SECRET = "SomeSecretYouProbablyWantToMakeConfigurable"
//const val AUTH_COOKIE = "AUTH_COOKIE"

class JWTAuthenticationFilter(private val authManager: AuthenticationManager) : UsernamePasswordAuthenticationFilter(){
	override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
		// When a POST request to /login is made, this method gets called. We are using a login form to authenticate
		// users, so lets grab the username and password that was provided in the form.
		val username = request.getParameter("username")
		val password = request.getParameter("password")
		// Get spring to authenticate the provided username and password.
		return authManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
	}

	override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse,
	                                      chain: FilterChain, authResult: Authentication?) {

		val user = authResult?.principal as? User ?:
		throw IllegalArgumentException("authResult cannot be null and must be an instance of User")

		// Create the JWT token and store the JWT in a cookie.
		val token = com.auth0.jwt.JWT.create()
			.withSubject(user.username)
			.withExpiresAt(Date(System.currentTimeMillis() + EXPIRATION_TIME))
			.sign(Algorithm.HMAC512(SECRET))
		response.addCookie(Cookie(AUTH_COOKIE, token))
		chain.doFilter(request, response)
	}

}