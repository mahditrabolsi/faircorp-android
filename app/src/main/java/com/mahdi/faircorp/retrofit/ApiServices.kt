package com.mahdi.faircorp.retrofit

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


const val API_USERNAME = "mahdi"
const val API_PASSWORD = "user"

object ApiServices {
    //authenticate to the server
    val credentials = Credentials.basic(API_USERNAME, API_PASSWORD)
    val interceptor = Interceptor { chain ->
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("Authorization", credentials).build()
        chain.proceed(authenticatedRequest)
    }
    val logging = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    val client = OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(logging)
        .build()
    val buildingApiService : BuildingApiService by lazy {

        Retrofit.Builder()
            .baseUrl("https://mahditrabolsi.cleverapps.io/api/")
            .addConverterFactory(MoshiConverterFactory.create())

            .client(client)
            .build()
            .create(BuildingApiService::class.java)
    }
    val roomApiService : RoomApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://mahditrabolsi.cleverapps.io/api/")
            .client(client)
            .build()
            .create(RoomApiService::class.java)
    }
    val windowApiService : WindowApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://mahditrabolsi.cleverapps.io/api/")
            .client(client)
            .build()
            .create(WindowApiService::class.java)
    }
    val heaterApiService : HeaterApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://mahditrabolsi.cleverapps.io/api/")
            .client(client)
            .build()
            .create(HeaterApiService::class.java)
    }

}