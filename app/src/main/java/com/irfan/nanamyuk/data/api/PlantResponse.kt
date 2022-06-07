package com.irfan.nanamyuk.data.api

import com.google.gson.annotations.SerializedName

data class PlantResponse(

	@field:SerializedName("PlantResponse")
	val plantResponse: List<PlantResponseItem>
)

data class PlantResponseItem(
	@field:SerializedName("Nama Tanaman")
	val namaTanaman: String,

	@field:SerializedName("Image")
	val image: String,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("Deskripsi")
	val deskripsi: String,

	@field:SerializedName("Suhu")
	val suhu: String,

	@field:SerializedName("Durasi Siram")
	val durasiSiram: String,

	@field:SerializedName("Tanah")
	val tanah: String,

	@field:SerializedName("Cahaya")
	val cahaya: String,

	@field:SerializedName("Kelembapan")
	val kelembapan: String,

	@field:SerializedName("Rainfall")
	val rainfall: String
)
