package com.example.myapplication.api

import com.example.myapplication.helper.Constanta
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private val client = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().
    setLevel(HttpLoggingInterceptor.Level.BODY)).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constanta.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }

}