package com.mahdi.faircorp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mahdi.faircorp.model.Room

@Dao
interface RoomDao {
    @Query("select * from room_table order by room_name")
    fun findAll(): List<Room>

    @Query("select * from room_table where building_id = :buildingId order by room_name")
    fun findAllRoomsByBuilding(buildingId: Long): List<Room>

    @Insert
    suspend fun create(room: Room)

    @Query("select * from room_table where id = :id")
    fun findById(id: Long): Room?

    @Query("delete from room_table where building_id = :buildingId")
    suspend fun clearByBuildingId(buildingId: Long)

    //delete by id
    @Query("DELETE FROM room_table WHERE id = :id")
    suspend fun deleteById(id: Long)


}