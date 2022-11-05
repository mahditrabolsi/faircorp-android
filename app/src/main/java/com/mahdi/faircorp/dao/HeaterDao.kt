package com.mahdi.faircorp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mahdi.faircorp.model.Heater

@Dao
interface HeaterDao {
    @Query("select * from heater order by name")
    fun findAll(): List<Heater>

    @Query("select * from heater where room_id = :roomId order by name")
    fun findHeatersByRoomId(roomId: Long): List<Heater>

    @Query("select * from heater where id = :id")
    fun findById(id: Long): Heater?

    @Insert
    suspend fun create(heater: Heater)

    @Query("delete from heater where room_id = :roomId")
    suspend fun clearByRoomId(roomId: Long)


    @Query("select * from heater where heater_status = :status")
    fun findAllHeatersByStatus(status: String): List<Heater>

    @Query("DELETE FROM heater WHERE id = :id")
    suspend fun deleteById(id: Long)
}