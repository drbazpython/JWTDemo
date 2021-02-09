package com.drbaz.auth.demo.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@Table(name="users")
@JsonIgnoreProperties("hibernateLazyInitializer","handler")
class AppUser (
	@Id
	@Column(name="id", nullable=false)
	@SequenceGenerator(name="user_seq",sequenceName="user_id_seq")
	@GeneratedValue(strategy=GenerationType.AUTO)
	var id:Long? = null,
	var username:String? = null,
	var password: String? = null,
	var firstname: String? = null,
	var lastname: String? = null
)