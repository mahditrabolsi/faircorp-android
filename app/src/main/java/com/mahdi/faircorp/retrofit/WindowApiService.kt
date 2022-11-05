package com.mahdi.faircorp.retrofit

import com.mahdi.faircorp.dto.WindowDto
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WindowApiService {
    @GET("windows")
    fun findAll(): Call<List<WindowDto>>

    @GET("windows/room/{room_id}")
    fun findWindowsByRoomId(@Path("room_id") room_id: Long): Call<List<WindowDto>>

    @POST("windows/switch/{window_id}")
    fun switchStatus(@Path("window_id") window_id: Long): Call<WindowDto>

    //create
    @POST("windows")
    fun createWindow(window: WindowDto): Call<WindowDto>

    @DELETE("windows/{window_id}")
    fun deleteById(@Path("window_id") window_id: Long): Call<Void>
}