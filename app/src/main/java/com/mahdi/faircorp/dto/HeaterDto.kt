package com.mahdi.faircorp.dto

data class HeaterDto(
    val id: Long?=null,
    val name: String,
    val power: Long?,
    val roomId: Long,
    val heaterStatus: HeaterStatus
)
