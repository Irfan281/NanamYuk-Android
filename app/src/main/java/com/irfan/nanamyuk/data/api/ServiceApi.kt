package com.irfan.nanamyuk.data.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ServiceApi {
    @POST("auth/login")
    fun postLogin(
        @Body params: HashMap<String, String>
    ) : Call<AuthResponse>

    @POST("auth/register")
    fun postRegister(
        @Body params: HashMap<String, String>
    ) : Call<AuthResponse>
}