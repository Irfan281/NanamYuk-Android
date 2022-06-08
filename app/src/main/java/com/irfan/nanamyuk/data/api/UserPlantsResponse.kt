package com.irfan.nanamyuk.data.api

import com.google.gson.annotations.SerializedName

data class UserPlantsResponse(

	@field:SerializedName("UserPlantsResponse")
	val userPlantsResponse: List<UserPlantsResponseItem>
)

data class UserPlantsResponseItem(

	@field:SerializedName("Nama Penanda")
	val namaPenanda: String,

	@field:SerializedName("Plant")
	val plant: List<PlantItem>,

	@field:SerializedName("Date")
	val date: String,

	@field:SerializedName("State")
	val state: Boolean,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("User")
	val user: List<UserItem>


)

data class PlantItem(
	@field:SerializedName("Image")
	val image: String,

	@field:SerializedName("Nama Tanaman")
	val namaTanaman: String,

	@field:SerializedName("Durasi Siram")
	val durasiSiram: String,

	@field:SerializedName("_id")
	val id: String
)

data class UserItem(
	@field:SerializedName("firstName")
	val firstName: String,

	@field:SerializedName("lastName")
	val lastName: String,

	@field:SerializedName("_id")
	val id: String,
)
