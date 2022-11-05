package com.mahdi.faircorp.model

import androidx.room.TypeConverter
import com.mahdi.faircorp.dto.HeaterStatus
import com.mahdi.faircorp.dto.WindowStatus

class EnumConverters {

    @TypeConverter
    fun fromWindowStatus(value: WindowStatus?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toWindowStatus(value: String?): WindowStatus? {
        return value?.let { WindowStatus.valueOf(it) }
    }

    @TypeConverter
    fun fromHeaterStatus(value: HeaterStatus?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toHeaterStatus(value: String?): HeaterStatus? {
        return value?.let { HeaterStatus.valueOf(it) }
    }

}