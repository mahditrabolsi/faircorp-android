package com.mahdi.faircorp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val API_USERNAME = "user"
const val API_PASSWORD = "password"
object ApiServices {
    val buildingApiService : BuildingApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://mahditrabolsi.cleverapps.io/api/")
            .build()
            .create(BuildingApiService::class.java)
    }
    val roomApiService : RoomApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://mahditrabolsi.cleverapps.io/api/")
            .build()
            .create(RoomApiService::class.java)
    }

}