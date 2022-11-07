package com.mahdi.faircorp.retrofit

import com.mahdi.faircorp.dto.HeaterDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface HeaterApiService {
    @GET("heaters")
    fun findAll(): Call<List<HeaterDto>>

    @GET("heaters/room/{room_id}")
    fun findHeatersByRoomId(@Path("room_id") room_id: Long): Call<List<HeaterDto>>

    @POST("heaters/switch/{heater_id}")
    fun switchStatus(@Path("heater_id") heater_id: Long): Call<HeaterDto>
    //create heater
    @POST("heaters")
    fun createHeater(@Body heater: HeaterDto): Call<HeaterDto>

    //delete heater
    @DELETE("heaters/{id}")
    fun deleteHeater(@Path("id") id: Long): Call<Void>
}