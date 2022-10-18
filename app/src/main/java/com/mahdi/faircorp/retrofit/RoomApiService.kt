package com.mahdi.faircorp.retrofit

import com.mahdi.faircorp.dto.RoomDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RoomApiService {
    @GET("rooms")
    fun findAll(): Call<List<RoomDto>>

    @GET("rooms/building/{building_id}")
    fun findById(@Path("building_id") id: Long): Call<List<RoomDto>>
}