package com.mahdi.faircorp.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mahdi.faircorp.model.*

@Database(entities = [Window::class, Room::class,Building::class,Heater::class], version = 1)
@TypeConverters(EnumConverters::class)
abstract class FaircorpDatabase : RoomDatabase() {
    abstract fun windowDao(): WindowDao
    abstract fun roomDao(): RoomDao
    abstract fun buildingDao(): BuildingDao
    abstract fun heaterDao(): HeaterDao
}