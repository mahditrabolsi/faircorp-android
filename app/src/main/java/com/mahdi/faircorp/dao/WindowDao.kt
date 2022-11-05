package com.mahdi.faircorp.dao

import androidx.room.*
import com.mahdi.faircorp.model.Window

@Dao
interface WindowDao {
    @Query("select * from rwindow order by name")
    fun findAll(): List<Window>

    @Query("select * from rwindow where roomId = :roomId order by name")
    fun findByRoomId(roomId: Long): List<Window>

    @Insert
    suspend fun create(window: Window)


    @Query("delete from rwindow where roomId = :roomId")
    suspend fun clearByRoomId(roomId: Long)

    //delete by id
    @Query("DELETE FROM rwindow WHERE id = :id")
    suspend fun deleteById(id: Long)
}