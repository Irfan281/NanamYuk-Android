package com.irfan.nanamyuk.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConfigApi {
    companion object {
        private var BASE_URL: String = "https://api.kontenbase.com/query/api/v1/32f30ee3-198c-4936-84d4-c21e1b38927c/"

        fun getApiService(): ServiceApi {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit =
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            return retrofit.create(ServiceApi::class.java)
        }
    }
}