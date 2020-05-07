package com.tiimhealthcare.patientappointment.database

import androidx.room.TypeConverter
import com.tiimhealthcare.patientappointment.model.Gender
import java.util.*

class Converters {

    @TypeConverter
    fun toDate(timestamp: Long?): Date? = if (timestamp == null) null else Date(timestamp)

    @TypeConverter
    fun toTimestamp(date: Date?): Long? = date?.time


    @TypeConverter
    fun toString(gender: Gender): String = gender.name

    @TypeConverter
    fun toGender(genderValue: String): Gender = Gender.valueOf(genderValue)
}