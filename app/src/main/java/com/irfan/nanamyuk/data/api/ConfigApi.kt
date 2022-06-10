package com.irfan.nanamyuk.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConfigApi {
    companion object {

        var BASE_URL: String = "https://api.kontenbase.com/query/api/v1/32f30ee3-198c-4936-84d4-c21e1b38927c/"
        var BASE_ML: String = "https://nanamyuk-g5ck3ypmca-as.a.run.app/"

        fun getApiService(url: String): ServiceApi {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit =
                Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            return retrofit.create(ServiceApi::class.java)
        }
    }
}