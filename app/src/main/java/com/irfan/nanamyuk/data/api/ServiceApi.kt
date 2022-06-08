package com.irfan.nanamyuk.data.api

import retrofit2.Call
import retrofit2.http.*


interface ServiceApi {
    @POST("auth/login")
    fun postLogin(
        @Body params: HashMap<String, String>
    ) : Call<AuthResponse>

    @POST("auth/register")
    fun postRegister(
        @Body params: HashMap<String, String>
    ) : Call<AuthResponse>

    @GET("Plant")
    fun getPlant(
        @Header("Authorization") header: String
    ) : Call<List<PlantResponseItem>>

    @GET("Plant/{id}")
    fun getPlantById(
        @Header("Authorization") header: String,
        @Path("id") id: String
    ) : Call<PlantResponseItem>

    @GET("UserPlants?\$lookup=*")
    fun getUserPlants(
        @Header("Authorization") header: String,
    ) : Call<List<UserPlantsResponseItem>>

    @POST("UserPlants")
    fun postUserPlants(
        @Header("Authorization") header: String,
        @Body params: HashMap<String, Any>
    ) : Call<UserPlantsResponseItem>

    @POST("auth/logout")
    fun logout(
        @Header("Authorization") header: String,
    ) : Call<AuthResponse>

    @PATCH("UserPlants/{id}")
    fun updateUserPlants(
        @Header("Authorization") header: String,
        @Path("id") id: String,
        @Body params: HashMap<String, Any>
    ): Call<UserPlantsResponseItem>
}