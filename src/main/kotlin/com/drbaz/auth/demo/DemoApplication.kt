package com.drbaz.auth.demo

import com.drbaz.auth.demo.entity.AppUser
import com.drbaz.auth.demo.repository.AppUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import mu.KotlinLogging


@SpringBootApplication
class DemoApplication (val userRepository: AppUserRepository
                      ): ApplicationRunner {
	// create two users in Postgres database
	// database is dropped and re-created every run
	@Autowired
	lateinit var passwordEncoder: BCryptPasswordEncoder
	private val logger = KotlinLogging.logger{}

	override fun run(args: ApplicationArguments) {
		//logger.trace{ "Dropped database tables and recreated" }
		val user1 = AppUser(9999, "barrie", passwordEncoder.encode("Hydrogen1"),"Barrie","Smith")
		val user2 = AppUser(8888, "hazel", passwordEncoder.encode("Hydrogen1"), "Hazel", "Smith")
		userRepository.save(user1)
		userRepository.save(user2)
		//logger.trace{"Created two users: ${user1.username} and ${user2.username}"}
	}

	@Bean
	fun bCryptPasswordEncoder():BCryptPasswordEncoder{
		return BCryptPasswordEncoder()
	}
}
	fun main(args: Array<String>) {
		//val app = SpringApplication(DemoApplication::class.java)
		//app.webApplicationType = WebApplicationType.REACTIVE
		//app.run(*args)
		runApplication<DemoApplication>(*args)
	}

