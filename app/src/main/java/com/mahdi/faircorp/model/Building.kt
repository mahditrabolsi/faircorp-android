package com.mahdi.faircorp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mahdi.faircorp.dto.BuildingDto

@Entity(tableName = "building")
data class Building(
    @PrimaryKey val id: Long?,
    @ColumnInfo(name = "building_name") val name: String,
    @ColumnInfo(name = "building_address") val address: String
){
    fun toDto(): BuildingDto =
        BuildingDto(id, name, address)
}
