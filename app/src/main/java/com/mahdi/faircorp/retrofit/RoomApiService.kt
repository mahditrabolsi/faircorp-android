package com.mahdi.faircorp.retrofit

import com.mahdi.faircorp.dto.RoomDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RoomApiService {
    @GET("rooms")
    fun findAll(): Call<List<RoomDto>>

    @GET("rooms/building/{building_id}")
    fun findById(@Path("building_id") id: Long): Call<RoomDto>

    @GET("rooms/building/{building_id}")
    fun findAllRoomsByBuilding(@Path("building_id") buildingId: Long): Call<List<RoomDto>>

    //create room
    @POST("rooms")
    fun createRoom(@Body room: RoomDto): Call<RoomDto>

    //delete room
    @DELETE("rooms/{id}")
    fun deleteRoom(@Path("id") id: Long): Call<Void>
}