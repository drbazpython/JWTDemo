package com.drbaz.auth.demo.service

import com.drbaz.auth.demo.repository.AppUserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: AppUserRepository) : UserDetailsService {

	override fun loadUserByUsername(username: String): UserDetails {
		val user = userRepository.findByUsername(username) ?: throw UsernameNotFoundException(username)
		return User(user.username, user.password, listOf())
	}
}