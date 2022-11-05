package com.mahdi.faircorp.dto

data class RoomDto(
    val id: Long,
    val name: String?,
    val floor: Long?,
    val currentTemperature: Double?,
    val targetTemperature: Double?,
    val buildingId: Long?
)