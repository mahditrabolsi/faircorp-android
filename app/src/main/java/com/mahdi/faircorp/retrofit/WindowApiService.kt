package com.mahdi.faircorp.retrofit

import com.mahdi.faircorp.dto.WindowDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WindowApiService {
    @GET("windows")
    fun findAll(): Call<List<WindowDto>>

    @GET("windows/{id}")
    fun findById(@Path("id") id: Long): Call<WindowDto>
}