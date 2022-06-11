package com.irfan.nanamyuk.data.api

import com.google.gson.annotations.SerializedName

data class RecomResponse(

	@field:SerializedName("1")
	val plant1: String,

	@field:SerializedName("2")
	val plant2: String,
	
	@field:SerializedName("3")
	val plant3: String,

	@field:SerializedName("4")
	val plant4: String,

	@field:SerializedName("5")
	val plant5: String,

	@field:SerializedName("response")
	val response: Int
)
