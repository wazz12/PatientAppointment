package com.tiimhealthcare.patientappointment.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tiimhealthcare.patientappointment.model.Gender
import java.util.*

@Entity(tableName = "patient_appointments")
data class PatientAppointment(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @TypeConverters(Converters::class)
    @ColumnInfo(name = "birthDate")
    val birthDate: Date,
    @ColumnInfo(name = "gender")
    val gender: Gender,
    @TypeConverters(Converters::class)
    @ColumnInfo(name = "appointmentDate")
    val appointmentDate: Date
)