package com.drbaz.auth.demo.controller

import com.drbaz.auth.demo.entity.AppUser
import com.drbaz.auth.demo.repository.AppUserRepository
import org.springframework.security.core.Authentication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

@Controller
class UserAppController @Autowired constructor (
	private val userRepository: AppUserRepository,
	private val passwordEncoder: BCryptPasswordEncoder
		)
{
	@GetMapping("/register")
	fun getRegistrationPage(): String{
		return "register"
	}
	@PostMapping("/register")
	fun regsiter(@RequestParam username: String,
	             @RequestParam password: String,
	             @RequestParam firstname: String,
	             @RequestParam lastname: String
	)
	//when user form is submitted, save user in database
	{
		val user = AppUser()
		user.username = username
		user.password = passwordEncoder.encode(password)
		user.firstname = firstname
		user.lastname = lastname
		userRepository.save(user)
	}

	@GetMapping("/login")
	fun getLoginPage(): String{
		return "login"
	}

	@GetMapping("/dashboard")
	fun getDashboardPage(authentication:Authentication): ModelAndView{
		val username = authentication.name
		val user = userRepository.findByUsername(username)
		return ModelAndView("dashboard", mapOf("user" to user))
	}
}