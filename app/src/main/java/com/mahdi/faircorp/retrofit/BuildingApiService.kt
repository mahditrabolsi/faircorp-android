package com.mahdi.faircorp.retrofit

import com.mahdi.faircorp.dto.BuildingDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BuildingApiService {
    @GET("buildings")
    fun findAll(): Call<List<BuildingDto>>

    @GET("buildings/{id}")
    fun findById(@Path("id") id: Long): Call<BuildingDto>


    @POST("buildings")
    fun createBuilding(@Body building: BuildingDto): Call<BuildingDto>

    @DELETE("buildings/{id}")
    fun deleteBuilding(@Path("id") id: Long): Call<Void>
}