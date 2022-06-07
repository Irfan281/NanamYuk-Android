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

    @GET("UserPlants?\$lookup=*")
    fun getUserPlants(
        @Header("Authorization") header: String,
    ) : Call<List<UserPlantsResponseItem>>
}