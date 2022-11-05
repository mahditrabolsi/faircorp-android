package com.mahdi.faircorp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mahdi.faircorp.dto.RoomDto
import com.mahdi.faircorp.dto.WindowDto
import com.mahdi.faircorp.dto.WindowStatus


@Entity(tableName = "rwindow")
data class Window(
    @PrimaryKey val id: Int,
    @ColumnInfo val name: String,
    @ColumnInfo(name = "roomId") val roomId: Long,
    @ColumnInfo(name = "roomName") val roomName: String,
    @ColumnInfo(name = "window_status") val windowStatus: WindowStatus
) {
    fun toDto(): WindowDto =
        WindowDto(id.toLong(), name,
            roomId,
            roomName,
            windowStatus)
}