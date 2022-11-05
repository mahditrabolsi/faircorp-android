package com.mahdi.faircorp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mahdi.faircorp.model.Building

@Dao
interface BuildingDao {
    @Query("select * from building order by building_name")
    fun findAll(): List<Building>

    @Query("select * from building where id = :id")
    fun findById(id: Long): Building?
    @Insert
    suspend fun create(building: Building)

    @Query("DELETE FROM building")
    suspend fun clearAll()

    //delete by id
    @Query("DELETE FROM building WHERE id = :id")
    suspend fun deleteById(id: Long)


}