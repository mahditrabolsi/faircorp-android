package com.mahdi.faircorp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mahdi.faircorp.dto.HeaterDto
import com.mahdi.faircorp.dto.HeaterStatus
import com.mahdi.faircorp.dto.RoomDto

@Entity(tableName = "heater")
data class Heater(
    @PrimaryKey val id: Int,
    @ColumnInfo val name: String,
    @ColumnInfo(name = "room_id") val roomId: Long,
    @ColumnInfo(name = "power") val power: Long?,
    @ColumnInfo(name = "heater_status") val heaterStatus: HeaterStatus
) {
    fun toDto(): HeaterDto =
        HeaterDto(
            id.toLong(), name, power,
                roomId,
                 heaterStatus
        )

}