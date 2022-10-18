package com.mahdi.faircorp.dto

data class HeaterDto(
    val id: Long,
    val name: String,
    val power: Int?,
    val room: RoomDto,
    val heaterStatus: HeaterStatus
)
