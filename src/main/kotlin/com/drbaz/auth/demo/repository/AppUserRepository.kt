package com.drbaz.auth.demo.repository

import com.drbaz.auth.demo.entity.AppUser
import org.springframework.data.jpa.repository.JpaRepository

interface AppUserRepository: JpaRepository<AppUser,Long> {
	fun findByUsername(username: String) :AppUser?
}