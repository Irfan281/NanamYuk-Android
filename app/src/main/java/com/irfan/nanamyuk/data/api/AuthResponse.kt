package com.irfan.nanamyuk.data.api

import com.google.gson.annotations.SerializedName

data class AuthResponse(
	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("token")
	val token: String,

	@field:SerializedName("message")
	val message: String
)

data class User(
	@field:SerializedName("firstName")
	val firstName: String,

	@field:SerializedName("lastName")
	val lastName: String,

	@field:SerializedName("email")
	val email: String,
)
