package com.mahdi.faircorp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mahdi.faircorp.dto.RoomDto


@Entity(tableName = "room_table")
data class Room(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "room_name") val name: String?,
    @ColumnInfo(name = "room_floor") val floor: Long?,
    @ColumnInfo(name = "room_current_temperature") val currentTemperature: Double?,
    @ColumnInfo(name = "room_target_temperature") val targetTemperature: Double?,
    @ColumnInfo(name = "building_id") val buildingId: Long?
){
    fun toDto(): RoomDto =
        RoomDto(id.toLong(), name, floor, currentTemperature, targetTemperature, buildingId)
}